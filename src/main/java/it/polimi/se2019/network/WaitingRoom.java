package it.polimi.se2019.network;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.configureMessage.HandlerConfigMessage;
import it.polimi.se2019.view.configureMessage.EnemyMessage;
import it.polimi.se2019.view.configureMessage.StatusLoginMessage;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.Timer;
import java.util.List;
import java.util.LinkedList;
import java.util.TimerTask;

public class WaitingRoom {
    private static int matchID = 100;
    private static WaitingRoom instance = null;
    private static List<GameHandler> macthes = new LinkedList<>();
    private final int duration;
    private List<WaitingPlayer> playerWaiting = new LinkedList<>();
    private int playerID = 0;
    private boolean isFirst = true;
    private Timer timer;

    private WaitingRoom(int duration) {
        this.duration = duration*1000;
        macthes.add(new GameHandler(matchID++));
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
            else networkHandler.update(null, new StatusLoginMessage(false, false));
        } catch (DisconnectedException e) {
            reconnectPlayer(e.gameHandler, e.nickname);
        }
    }

    /**
     * Check the uniqueness of the nickname
     * @param nickname the nickname to check
     * @return true if is unique, false other way
     */
    private synchronized boolean checkNickname(String nickname, int matchID) throws DisconnectedException {
        for(WaitingPlayer wp : playerWaiting) {
            if(wp.getPlayer().getNickname().equals(nickname)) return false;
        }
        for(GameHandler gm : macthes) {
            for(Player p : gm.getOrderPlayerList()) {
                if(p.getNickname().equals(nickname)) {
                    //TODO handle disconnection
                    if(gm.checkMatchID(matchID)/*&&gm.isDisconnected()*/) throw new DisconnectedException();
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
        Controller controller = new Controller(macthes.get(macthes.size()-1));
        playerView.attach(controller);

        synchronized(this) {
            while(playerWaiting.size()==5) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  //Does it work??
                }
            }
            playerWaiting.add(new WaitingPlayer(player, playerView, controller, networkHandler, new EnemyView(nickname)));
            if(playerWaiting.size() == 3) setTimer();
            if(playerWaiting.size() == 5) startMatch();

            if(isFirst) networkHandler.update(null, new StatusLoginMessage(true, true));
            else networkHandler.update(null, new StatusLoginMessage(true, false));
        }
    }

    /**
     * Reconnect a player to his match
     * @param gm Match where to be reconnected
     * @param nickname Nickname of the player
     */
    private void reconnectPlayer(GameHandler gm, String nickname) {

    }

    private void setTimer() {
        if(timer==null) timer = new Timer();
        else timer.cancel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(playerWaiting.size()>=3) startMatch();
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
        GameHandler gm = macthes.get(macthes.size() - 1);
        //Create all views and attach networkHandler
        MapView mapView = new MapView();
        SkullBoardView skullBoardView = new SkullBoardView();
        List<EnemyView> enemyViews = new LinkedList<>();
        for (WaitingPlayer wp : playerWaiting) {
            mapView.attach(wp.getNetworkHandler());
            skullBoardView.attach(wp.getNetworkHandler());
            enemyViews.add(wp.getEnemyView());
            for (WaitingPlayer wp2 : playerWaiting) {
                //Attach each enemy view at the network handler of the ENEMY (not at himself)
                if (!wp.getEnemyView().getNickname().equals(wp2.getPlayer().getNickname())) {
                    wp2.getNetworkHandler().update(null, new EnemyMessage(wp2.getEnemyView().getNickname()));
                }
            }
            gm.setUp(wp.getPlayer(), wp.getPlayerView());
            gm.attachAll(mapView, skullBoardView, enemyViews);
        }

        gm.start();
        playerWaiting.clear();
        notifyAll(); //Wake threads that are waiting the playerWaiting list is <5
        isFirst = true;
        timer.cancel();
        timer = null;
        macthes.add(new GameHandler(matchID++));
    }

    public void handleDisconnectMessage() {

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
            GameHandler gm = macthes.get(macthes.size() - 1);
            gm.setMap(map);
            gm.setSkull(skulls);
            gm.setSuddenDeath(suddenDeath);

            isFirst = false;
        }
    }
}