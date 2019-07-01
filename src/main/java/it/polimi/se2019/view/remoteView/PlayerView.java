package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.network.messages.PossibleCharacterMessage;
import it.polimi.se2019.view.ModelViewMess.HandlerPlayerViewMessage;
import it.polimi.se2019.view.View;

import java.util.ArrayList;
import java.util.List;


public class PlayerView extends View /*View implement observer/observable*/{
    private Player playerCopy;
    private ArrayList<Character> possibleCharacter;
    private Server networkHandler;

    //for tests
    private String lastStringPrinted;

    public PlayerView(Server networkHandler, Player clone) {
        this.networkHandler = networkHandler;
        this.playerCopy = clone;
        networkHandler.setPlayerView(this);
    }

    /**
     * to initialize player
     * @param player to initialize
     */
    public PlayerView(Player player) {
        this.playerCopy = player;
    }

    /**
     * getter of playerCopy
     * @return playerCopy
     */
    public Player getPlayerCopy() {
        return playerCopy;
    }

    /**
     * Receive message from the model and forward it to the networkHandler
     * @param o null
     * @param arg the message receive to forward
     */
    @Override
    public void update(java.util.Observable o /*will be always NULL*/, Object arg) {
        HandlerPlayerViewMessage message = (HandlerPlayerViewMessage) arg;
        message.handleMessage(this);
        if(networkHandler!=null) networkHandler.update(null, message);
    }

    /**
     * used for tests, it prints the last string received from printFromController
     * @return the String printed
     */
    public String getLastStringPrinted() {
        return lastStringPrinted;
    }


    /**
     * Receive a string message from the controller and forward it to the networkHandler
     * @param string the message
     */
    @Override
    public void printFromController(String string) {
        this.lastStringPrinted = string; //For testing
        if(networkHandler!=null) networkHandler.send(string);
    }

    /**
     * Set the attribute playerCopy to the new playerCopy
     * @param p the new playerCopy
     */
    public void handlePlayerMessage(Player p) {
        playerCopy = p;
    }

    /**
     * Do nothing
     */
    @Override
    public void handleTurnMessage(String nickname) {
        //Is only to forward, already done by update()
    }

    /**
     * Do nothing
     * @param ranking list of player ordered by ranking
     */
    @Override
    public void handleRankingMessage(List<Player> ranking) {
        //Is only to forward, already done by update()
    }

    /**
     * Forward the possible character to the network handler
     * @param characters A list of character form which chose
     */
    @Override
    public void setPossibleCharacter(List<Character> characters) {
        if(networkHandler!=null) networkHandler.update(null, new PossibleCharacterMessage(characters));
    }

    /**
     * Usual setter for the NetworkHandler attribute
     * To use in case of reconnection
     * @param networkHandler The network handler to set
     */
    //To use for reconnection
    public void setNetworkHandler(Server networkHandler) {
        this.networkHandler = networkHandler;
    }

    /**
     * Set or cancel a timer for this user.
     * When the timer is out, the user will be disconnected from the match.
     * @param set True if wanna set the timer, false for cancel it.
     */
    public void setTimer(boolean set) {
        if(set) networkHandler.setTimer();
        else networkHandler.cancelTimer();
    }

}