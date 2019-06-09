package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.*;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ModelViewMess.HandlerPlayerViewMessage;
import it.polimi.se2019.view.ModelViewMess.PossibleTargetMessage;
import it.polimi.se2019.view.ModelViewMess.StartGameMessage;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;
import java.util.List;


public class PlayerView extends View /*View implement observer/observable*/{
    private Player playerCopy;
    private ArrayList<? extends Target> possibleTargets;
    private ArrayList<? extends Target> selectedTargets;
    private ArrayList<Character> possibleCharacter;
    private Character choosenCharacter;
    private Server networkHandler;

    public PlayerView(Server networkHandler, Player clone) {
        this.networkHandler = networkHandler;
        this.playerCopy = clone;
    }

    public PlayerView(Player player) {
        this.playerCopy = player;
    }

    public Player getPlayerCopy() {
        return playerCopy;
    }

    public ArrayList<? extends Target> getSelectedTargets() {
        return selectedTargets;
    }

    @Override
    public void update(java.util.Observable o /*will be always NULL*/, Object arg) {
        HandlerPlayerViewMessage message = (HandlerPlayerViewMessage) arg;
        message.handleMessage(this);
        networkHandler.update(null, message);
    }

    public Character getChoosenCharacter() {
        return choosenCharacter;
    }

    public ArrayList<Character> getPossibleCharacter() {
        return possibleCharacter;
    }

    public ArrayList<? extends Target> getPossibleTargets() {
        return possibleTargets;
    }

    public void setPossibleTargets(List<? extends Target> targets){
        this.possibleTargets = (ArrayList<? extends Target>) targets;
        networkHandler.update(null, new PossibleTargetMessage(targets));
    }

    public void send (ViewControllerMessage message){
        notifyObservers(message);
    }

    @Override
    public void printFromController(String string) {
        networkHandler.send(string);
    }

    @Override
    public void handleStartGameMessage(StartGameMessage startGameMessage) {
        //Is only to forward, already done by update()
    }

    public void handlePlayerMessage(Player p) {
        playerCopy = p;
    }

    //To use for reconnection
    public void setNetworkHandler(Server networkHandler) {
        this.networkHandler = networkHandler;
    }

}