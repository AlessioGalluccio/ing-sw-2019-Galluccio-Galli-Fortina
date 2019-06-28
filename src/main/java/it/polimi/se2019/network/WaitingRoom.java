package it.polimi.se2019.network;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.messages.HandlerConfigMessage;
import it.polimi.se2019.view.ModelViewMess.MapMessage;
import it.polimi.se2019.view.ModelViewMess.PlayerModelMessage;
import it.polimi.se2019.view.ModelViewMess.SkullBoardMessage;
import it.polimi.se2019.view.ViewControllerMess.ReconnectionMessage;
import it.polimi.se2019.view.configureMessage.EnemyMessage;
import it.polimi.se2019.view.configureMessage.StartGameMessage;
import it.polimi.se2019.view.configureMessage.StatusLoginMessage;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.Timer;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitingRoom {
    private static int matchID = 100;
    private static WaitingRoom instance = null;
    private static List<Match> matches = new ArrayList<>();
    private final int duration;
    private List<WaitingPlayer> playerWaiting = new LinkedList<>();
    private int playerID = 0;
    private boolean isFirst = true;
    private Timer timer;

    private WaitingRoom(int duration) {
        this.duration = duration*1000;
        matches.add(new Match(matchID, new GameHandler(matchID)));
        matchID++;
    }

    /**
     * Since WaitingRoom is singleton, this method create new instance of WaitingRoom only if is the first time
     * @param duration Duration of timer in seconds
     * @return The instance of WaitingRoom
     * @throws IllegalArgumentException if duration <= 0
     */
    public static WaitingRoom create(int duration) {
        if(duration <= 0) throw new IllegalArgumentException();
        if(instance==null) instance = new WaitingRoom(duration);
        return instance;
    }

    List<WaitingPlayer> getPlayerWaiting() {
        return new LinkedList<>(playerWaiting);
    }

    /**
     * Receive a message from the player's server
     * @param message the message
     * @param sender the sender
     */
    public void receiveMessage(HandlerConfigMessage message, Server sender) {
        message.handleMessage(this, sender);
    }

    /**
     * Register a new user to the match
     * @param nickname the nickname chosen
     * @param matchID the match's id the user want to join
     * @param networkHandler the sender of the request
     */
    public void handleLoginMessage(String nickname, int matchID, Server networkHandler){
        try {
            if(checkNickname(nickname, matchID)) initializePlayer(nickname, networkHandler);
            else networkHandler.update(null, new StatusLoginMessage(false, false, nickname));
        } catch (DisconnectedException e) {
            networkHandler.update(null, new StatusLoginMessage(true, false, nickname));
            reconnectPlayer(e.nickname, matchID, networkHandler);
        }
    }

    /**
     * Check the uniqueness of the nickname
     * @param nickname the nickname to check
     * @return true if is unique, false other way
     */
    private synchronized boolean checkNickname(String nickname, int matchID) throws DisconnectedException {
        for(WaitingPlayer wp : playerWaiting) {
            if(wp.player.getNickname().equals(nickname)) return false;
        }
        for(Match m : matches) {
            for(Player p : m.gameHandler.getOrderPlayerList()) {
                if(p.getNickname().equals(nickname)) {
                    if(m.matchID==matchID &&
                    m.gameHandler.isDisconnected(nickname))  {
                        throw new DisconnectedException(m.gameHandler, nickname);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void initializePlayer(String nickname, Server networkHandler) {
        Player player = new Player(nickname, playerID++);
        PlayerView playerView = new PlayerView(networkHandler, player.clone());
        player.attach(playerView);
        EnemyView enemyView = new EnemyView(nickname);
        player.attach(enemyView);
        Controller controller = new Controller(matches.get(matches.size()-1).gameHandler, player, playerView);
        playerView.attach(controller);

        synchronized(this) {
            while(playerWaiting.size()==5) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            playerWaiting.add(new WaitingPlayer(player, playerView, controller, networkHandler, enemyView));
            if(playerWaiting.size() == 3) setTimer();
            if(playerWaiting.size() == 5) {
                while(matches.get(matches.size()-1).gameHandler.getMap()==null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                startMatch();
            }

            if(isFirst) networkHandler.update(null, new StatusLoginMessage(true, true, nickname));
            else networkHandler.update(null, new StatusLoginMessage(true, false, nickname));

            Logger.getLogger(WaitingRoom.class.getName()).log(Level.INFO, nickname + " has join");
        }
    }

    /**
     * Reconnect a player to his match
     * @param nickname Nickname of the player
     * @param matchID The match where to be reconnected
     */
    private void reconnectPlayer(String nickname, int matchID, Server networkHandler) {
        Match m;
        try {
            m = getMatchById(matchID);
            m.networkHandlers.add(networkHandler);
            networkHandler.setMatchID(m.matchID);
            m.skullBoardView.attach(networkHandler);
            m.mapView.attach(networkHandler);

            for(EnemyView ew : m.enemyViews) {
                if (!ew.getNickname().equals(nickname)) {
                    networkHandler.update(null, new EnemyMessage(ew.getNickname()));
                    ew.attach(networkHandler);
                }
            }

            networkHandler.update(null, new MapMessage(m.gameHandler.getMap().clone()));
            for(Player p : m.gameHandler.getOrderPlayerList()) {
                networkHandler.update(null, new PlayerModelMessage(p.clone()));
            }
            networkHandler.update(null, new SkullBoardMessage(m.gameHandler.getSkull(), m.gameHandler.cloneDeath()));
            for(PlayerView pw : m.playerViews) {
                if(pw.getPlayerCopy().getNickname().equals(nickname)) {
                    networkHandler.setPlayerView(pw);
                    pw.setNetworkHandler(networkHandler);
                    pw.notifyObservers(new ReconnectionMessage(true,pw.getPlayerCopy().getID(),pw));
                }
            }
            try {
                Thread.sleep(750);  //Wait all message arrive at the user
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            networkHandler.update(null, new StartGameMessage(m.matchID));
        } catch (NotMatchException e) {
            Logger.getLogger(WaitingRoom.class.getName()).log(Level.WARNING,
                    "Impossible reconnect" + nickname + "to match #" + matchID ,e);
        }
    }

    private void setTimer() {
        if(timer!=null) timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Logger.getLogger(WaitingRoom.class.getName()).log(Level.INFO, "Timeout.");
                if(playerWaiting.size()>=3 &&
                        matches.get(matches.size()-1).gameHandler.getMap()!=null) startMatch();
                timer=null;
            }
        }, duration);
    }

    /**
     * Create the remote view components and attach them to the server
     * Set up the game handler
     * In the end attach every observer to his observable
     */
    private synchronized void startMatch() {
        GameHandler gameHandler = matches.get(matches.size() - 1).gameHandler;
        Match currentMatch = matches.get(matches.size() - 1);
        //Create all views and attach networkHandler
        MapView mapView = new MapView();
        currentMatch.mapView = mapView;
        SkullBoardView skullBoardView = new SkullBoardView();
        currentMatch.skullBoardView = skullBoardView;

        List<EnemyView> enemyViews = new LinkedList<>();
        List<PlayerView> playerViews = new LinkedList<>();
        List<Server> networkHandlers = new LinkedList<>();

        for (WaitingPlayer wp : playerWaiting) {
            mapView.attach(wp.networkHandler);
            skullBoardView.attach(wp.networkHandler);
            enemyViews.add(wp.enemyView);
            networkHandlers.add(wp.networkHandler);
            playerViews.add(wp.playerView);
            wp.networkHandler.setMatchID(currentMatch.matchID);

            for(WaitingPlayer enemy : playerWaiting) {
                if(!enemy.enemyView.getNickname().equals(wp.player.getNickname())) {
                    wp.networkHandler.update(null, new EnemyMessage(enemy.enemyView.getNickname()));
                    enemy.enemyView.attach(wp.networkHandler);
                }
            }
            gameHandler.setUp(wp.player, wp.playerView, wp.controller);
            gameHandler.attachAll(mapView, skullBoardView, enemyViews);
        }

        currentMatch.playerViews = playerViews;
        currentMatch.enemyViews = enemyViews;
        currentMatch.networkHandlers = networkHandlers;

        gameHandler.start();

        setNewMatch();
        notifyAll(); //Wake threads that are waiting the playerWaiting list is < 5

        Logger.getLogger(WaitingRoom.class.getName()).log(Level.INFO,
                "Match #" + currentMatch.matchID + " has started" );
    }

    private void setNewMatch() {
        playerWaiting.clear();
        isFirst = true;
        timer.cancel();
        timer = null;
        matches.add(new Match(matchID, new GameHandler(matchID)));
        matchID++;
    }


    /**
     * Set the parameter of the player
     * If two player have access to this method, only the first one is served
     * @param map the number of the map chosen
     * @param skulls the number of skull of the game
     * @param suddenDeath if the player want play with the sudden death modality
     * @param sender who has send the setting
     */
    public synchronized void handleSettingMessage(int map, int skulls, boolean suddenDeath, Server sender) {
        if(isFirst) {
            GameHandler gm = matches.get(matches.size() - 1).gameHandler;
            gm.setMap(map);
            gm.setSkull(skulls);
            gm.setSuddenDeath(suddenDeath);
            isFirst = false;
            setTimer();
            notifyAll();
        }
        Logger.getLogger(WaitingRoom.class.getName()).log(Level.INFO, "Map&Co. set");
    }

    public void disconnect(Server server, PlayerView view, int matchID) {
        for(WaitingPlayer wp : playerWaiting) {
            if(wp.player.getNickname().equals(view.getPlayerCopy().getNickname())) {
                playerWaiting.remove(wp);
                Logger.getLogger(WaitingRoom.class.getName()).log(Level.INFO, "Disconnect " +
                        view.getPlayerCopy().getNickname());
                return;
            }
        }

        try {
            Match m = getMatchById(matchID);
            m.networkHandlers.remove(server);
            m.mapView.detach(server);
            m.skullBoardView.detach(server);
            view.setNetworkHandler(null);
            for(EnemyView enemyView : m.enemyViews) enemyView.detach(server);
            Logger.getLogger(WaitingRoom.class.getName()).log(Level.INFO, "Disconnect " +
                    view.getPlayerCopy().getNickname());
        } catch (NotMatchException e) {
            Logger.getLogger(WaitingRoom.class.getName()).log(Level.WARNING,
                    "Impossible disconnect " + view.getPlayerCopy().getNickname() + " from match #" + matchID ,e);
        }
    }

    public static void deleteMatch(GameHandler gameHandler) {
        try {
            Match m = getMatchById(gameHandler.getMatchID());
            for(Server networkHandler : m.networkHandlers) {
                networkHandler.closeAll();
            }
            if(matches.remove(m))
                Logger.getLogger(WaitingRoom.class.getName()).log(Level.INFO,
                    "Deleted match #" + gameHandler.getMatchID());
        } catch (NotMatchException e) {
            Logger.getLogger(WaitingRoom.class.getName()).log(Level.WARNING,
                    "Impossible delete match #" + gameHandler.getMatchID() ,e);
        }
    }

    private static Match getMatchById(int matchId) throws NotMatchException {
        for(Match m : matches) {
            if(m.matchID == matchId && matchId != matchID) return m;
        }
        throw new NotMatchException();
    }

    private class WaitingPlayer {
        Player player;
        PlayerView playerView;
        Controller controller;
        Server networkHandler;
        EnemyView enemyView;

        WaitingPlayer(Player player, PlayerView playerView, Controller controller, Server networkHandler, EnemyView enemyView) {
            this.player = player;
            this.playerView = playerView;
            this.controller = controller;
            this.networkHandler = networkHandler;
            this.enemyView = enemyView;
        }
    }

    private class Match {
        GameHandler gameHandler;
        int matchID;
        List<PlayerView> playerViews;
        List<EnemyView> enemyViews;
        List<Server> networkHandlers;
        MapView mapView;
        SkullBoardView skullBoardView;

        Match(int matchID, GameHandler gameHandler) {
            this.matchID = matchID;
            this.gameHandler = gameHandler;
        }
    }

}