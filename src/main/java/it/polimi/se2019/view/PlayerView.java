package it.polimi.se2019.view;

import it.polimi.se2019.model.player.Player;

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



    public void createTargetMessage(){
        TargetMessage message = new TargetMessage(ArrayList<Target>);
        notifyObservers(message);

    }

    public void createCellMessage(){
        CellMessage message = new CellMessage(int x, int y);
        notifyObservers(message);

    }

    public void createPlayerViewMessage(){

    }

    public void createCardSpawnChooseMessage(){

    }

    public void createNopeMessage(){

    }

    public void createMoveMessage(){

    }

    public void createShootMessage(){

    }

    public void createGrabMessage(){

    }

    public void createNewtonMessage(){

    }

    public void createReloadMessage(){

    }

    public void createTeleporterMessage(){

    }

    public void createTargetingScopeMessage(){

    }

    public void createTagbackGranadeMessage(){

    }



}