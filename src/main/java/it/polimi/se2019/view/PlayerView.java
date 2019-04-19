package it.polimi.se2019.view;

import it.polimi.se2019.model.deck.*;

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

    public PlayerView(Player playerCopy, ArrayList<Target> possibleTarget, ArrayList<Target> selectedTarget) {
        this.playerCopy = playerCopy;
        this.possibleTarget = possibleTarget;
        this.selectedTarget = selectedTarget;
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



    public void createTargetMessage(ArrayList<Target> target){
        TargetMessage message = new TargetMessage(target);
        notifyObservers(message);

    }

    public void createCellMessage(int x, int y){
        CellMessage message = new CellMessage(x,y);
        notifyObservers(message);

    }

    public void createPlayerViewMessage(int playerID){
        PlayerViewMessage message = new PlayerViewMessage(playerID);
        notifyObservers(message);
    }

    public void createCardSpawnChooseMessage(PowerupCard cardChoosen, PowerupCard cardDiscarded){
        CardSpawnChooseMessage message = new CardSpawnChooseMessage(cardChoosen, cardDiscarded);
        notifyObservers(message);
    }

    public void createNopeMessage(){
        NopeMessage message = new NopeMessage();
        notifyObservers(message);
    }

    public void createActionMessage(int actionID){
        ActionMessage message = new ActionMessage(actionID);
        notifyObservers(message);
    }


    public void createNewtonMessage(NewtonCard usedCard){
        NewtonMessage message = new NewtonMessage(usedCard);
        notifyObservers(message);
    }

    public void createReloadMessage(ArrayList<WeaponCard> weapon){
        ReloadMessage message = new ReloadMessage(weapon);
        notifyObservers(message);
    }

    public void createTeleporterMessage(TeleporterCard usedCard){
        TeleporterMessage message = new TeleporterMessage(usedCard);
        notifyObservers(message);
    }

    public void createTargetingScopeMessage(TargetingScopeCard usedCard){
        TargetingScopeMessage message = new TargetingScopeMessage(usedCard);
        notifyObservers(message);
    }

    public void createTagbackGranadeMessage(TagbackGranedCard usedCard){
        TagbackGranateMessage message = new TagbackGranateMessage(usedCard);
        notifyObservers(message);
    }

    public void createFireModeMessage(int firemodeID){
        FireModeMessage message = new FireModeMessage(firemodeID);
        notifyObservers(message);
    }


    public void createEndMessage (int messageID){
        EndMessage message = new EndMessage(messageID);
        notifyObservers(message);
    }

    public void createUndoMessage(int messageID){
        UndoMessage message = new UndoMessage(messageID);
        notifyObservers(message);
    }


    public void printFromController(String string) {

    }

    private boolean verifyTarget(){
        return true;
    }

}