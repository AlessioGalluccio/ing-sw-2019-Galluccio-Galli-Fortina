package it.polimi.se2019.view;

import it.polimi.se2019.model.deck.*;

import it.polimi.se2019.model.player.Player;

import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class PlayerView extends Observable implements Observer {
    private Player playerCopy;

    public PlayerView(Player playerCopy) {
        this.playerCopy = playerCopy;
    }

    public Player getPlayerCopy() {
        return playerCopy;
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

    public void printFromController(String string) {

    }


}