package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.*;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ModelViewMess.HandlerPlayerViewMessage;
import it.polimi.se2019.view.ModelViewMess.PossibleTargetMessage;
import it.polimi.se2019.view.View;

import java.util.ArrayList;
import java.util.List;


public class PlayerView extends View /*View implement observer/observable*/{
    private Player playerCopy;
    private ArrayList<? extends Target> possibleTargets;
    private ArrayList<? extends Target> selectedTargets;
    private ArrayList<Character> possibleCharacter;
    private Character choosenCharacter;
    private Server networkHandler;

    //for tests
    private String lastStringPrinted;

    public PlayerView(Server networkHandler, Player clone) {
        this.networkHandler = networkHandler;
        this.playerCopy = clone;
        networkHandler.setPlayerView(this);
    }

    public PlayerView(Player player) {
        this.playerCopy = player;
    }

    public Player getPlayerCopy() {
        return playerCopy;
    }

    public List<? extends Target> getSelectedTargets() {
        return selectedTargets;
    }

    public Character getChoosenCharacter() {
        return choosenCharacter;
    }

    public List<Character> getPossibleCharacter() {
        return possibleCharacter;
    }

    public List<? extends Target> getPossibleTargets() {
        return possibleTargets;
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
     * Set possible target to be aim by th weapon
     * Receive them from the controller and forward them to the networkHandler
     * @param targets possible target
     */
    public void setPossibleTargets(List<? extends Target> targets){
        this.possibleTargets = (ArrayList<? extends Target>) targets;
        if(networkHandler!=null) networkHandler.update(null, new PossibleTargetMessage(targets));
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
     * @param ranking
     */
    @Override
    public void handleRankingMessage(List<Player> ranking) {
        //Is only to forward, already done by update()
    }


    //To use for reconnection
    public void setNetworkHandler(Server networkHandler) {
        this.networkHandler = networkHandler;
    }

}