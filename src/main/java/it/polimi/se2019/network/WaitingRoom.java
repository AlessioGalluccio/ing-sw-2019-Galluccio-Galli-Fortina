package it.polimi.se2019.network;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.configureMessage.HandlerConfigMessage;
import it.polimi.se2019.view.configureMessage.StatusLoginMessage;
import it.polimi.se2019.view.configureMessage.isFirstMessage;
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
            else if(networkHandler!=null) networkHandler.update(null, new StatusLoginMessage(false));
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
        PlayerView playerView = new PlayerView(networkHandler);
        Player player = new Player(nickname, playerID++);
        player.attach(playerView);
        Controller controller = new Controller(macthes.get(macthes.size()-1));
        playerView.attach(controller);

        synchronized(playerWaiting) {
            playerWaiting.add(new WaitingPlayer(player, playerView, controller, networkHandler, new EnemyView(nickname)));
            if(playerWaiting.size() == 3) setTimer();
            if(playerWaiting.size() == 5) startMatch();
        }

        if(networkHandler!=null) {
            if(isFirst) networkHandler.update(null, new isFirstMessage());
            else networkHandler.update(null, new StatusLoginMessage(true));
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

    private void startMatch() {
        //Create all views and attach networkHandler
        MapView mapView = new MapView();
        SkullBoardView skullBoardView = new SkullBoardView();
        List<EnemyView> enemyViews = new LinkedList<>();
        for(WaitingPlayer wp : playerWaiting) {
            mapView.attach(wp.getNetworkHandler());
            skullBoardView.attach(wp.getNetworkHandler());
            enemyViews.add(wp.getEnemyView());
            for(WaitingPlayer wp2 : playerWaiting) {
                //Attach each enemy view at the network handler of the ENEMY (not at himself)
                if(!wp.getEnemyView().getNickname().equals(wp2.getPlayer().getNickname())) {
                    wp.getEnemyView().attach(wp2.getNetworkHandler());
                }
            }
            macthes.get(macthes.size() - 1).setUp(wp.getPlayer(), wp.getPlayerView());
            macthes.get(macthes.size() - 1).attachAll(mapView, skullBoardView, enemyViews);
        }

        //TODO NOTIFY PLAYERS MATCH IS STARTED!
        playerWaiting.clear();
        isFirst = true;
        macthes.add(new GameHandler(matchID++));

        //Do lock on playerWaiting
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
        if(sender!=null) sender.update(null, new StatusLoginMessage(true));
    }
}
