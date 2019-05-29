package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.*;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ModelViewMess.HandlerPlayerViewMessage;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;


public class PlayerView extends View /*View implement observer/observable*/{
    private Player playerCopy;
    private ArrayList<? extends Target> possibleTargets;
    private ArrayList<? extends Target> selectedTargets;
    private ArrayList<Character> possibleCharacter;
    private Character choosenCharacter;

    public PlayerView(Player playerCopy, ArrayList<Target> possibleTargets, ArrayList<Target> selectedTargets,
                      ArrayList<Character> possibleCharacter, Character choosenCharacter ) {

        this.playerCopy = playerCopy;
        this.possibleTargets = possibleTargets;
        this.selectedTargets = selectedTargets;
        this.possibleCharacter = possibleCharacter;
        this.choosenCharacter = choosenCharacter;
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
        //TODO Forward message to client -Call server.update(message)
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

    public void setPossibleTargets(ArrayList<? extends Target> targets){
        this.possibleTargets = targets;
    }

    public void send (ViewControllerMessage message){
        notifyObservers(message);
    }

    @Override
    public void printFromController(String string) {

    }

    public void handlePlayerMessage(Player p) {
        playerCopy = p;
    }
}