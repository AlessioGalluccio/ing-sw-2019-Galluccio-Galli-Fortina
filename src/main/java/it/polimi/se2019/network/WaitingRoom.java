package it.polimi.se2019.network;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.configureMessage.HandlerConfigMessage;
import it.polimi.se2019.view.configureMessage.StatusLoginMessage;
import it.polimi.se2019.view.configureMessage.isFirstMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.List;
import java.util.LinkedList;

public class WaitingRoom {
    private static int matchID = 100;
    private static WaitingRoom instance = null;
    private static List<GameHandler> macthes = new LinkedList<>();
    private final int duration;
    private List<WaintingPlayer> playerWaiting = new LinkedList<>();
    private int playerID = 0;
    private boolean isFirst = true;

    private WaitingRoom(int duration) {
        this.duration = duration;
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

    public void receiveMessage(HandlerConfigMessage message, Server sender) {
        message.handleMessage(this, sender);
    }

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
        for(WaintingPlayer wp : playerWaiting) {
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
        Controller controller = new Controller(macthes.get(macthes.size()-1));
        playerView.attach(controller);

        synchronized(playerWaiting) {
            playerWaiting.add(new WaintingPlayer(player, playerView, controller));
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

    }

    private void startMatch() {


        for(WaintingPlayer wp : playerWaiting) {
            macthes.get(macthes.size() - 1).setUp(wp.getPlayer());
        }

        playerWaiting.clear();
        isFirst = true;
        macthes.add(new GameHandler(matchID++));

        //Do lock on playerWaiting
    }

    public void handleDisconnectMessage() {

    }

    public void handleChooseParamMessage() {


        //Last row
        isFirst = false;
        //networkHandler.update(null, new StatusLogin(true));
    }

    public List<WaintingPlayer> getPlayerWaiting() {
        return new LinkedList<>(playerWaiting);
    }


}
