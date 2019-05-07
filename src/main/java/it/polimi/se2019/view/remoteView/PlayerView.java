package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.deck.*;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;

import it.polimi.se2019.view.ModelViewMess.PossibleTargetMessage;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class PlayerView extends Observable implements Observer {
    private Player playerCopy;
    private ArrayList<Target> possibleTarget;
    private ArrayList<Target> selectedTarget;
    private ArrayList<Character> possibleCharacter;
    private Character choosenCharacter;

    public PlayerView(Player playerCopy, ArrayList<Target> possibleTarget, ArrayList<Target> selectedTarget,
                      ArrayList<Character> possibleCharacter, Character choosenCharacter ) {

        this.playerCopy = playerCopy;
        this.possibleTarget = possibleTarget;
        this.selectedTarget = selectedTarget;
        this.possibleCharacter = possibleCharacter;
        this.choosenCharacter = choosenCharacter;
    }

    public Player getPlayerCopy() {
        return playerCopy;
    }

    public ArrayList<Target> getSelectedTarget() {
        return selectedTarget;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Character getChoosenCharacter() {
        return choosenCharacter;
    }

    public ArrayList<Character> getPossibleCharacter() {
        return possibleCharacter;
    }

    public ArrayList<Target> getPossibleTarget() {

        return possibleTarget;
    }

    public void send (ViewControllerMessage message){

        notifyObservers(message);

    }



    public void printFromController(String string) {


    }



}