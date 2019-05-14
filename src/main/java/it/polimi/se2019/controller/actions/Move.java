package it.polimi.se2019.controller.actions;


import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.CellMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

public class Move extends Action {

    private int newCoordinateX;
    private int newCoordinateY;

    public Move(GameHandler gameHandler) {
        super(gameHandler);
    }

    @Override
    public void executeAction(Player author, ArrayList<ViewControllerMessage> msg) throws IllegalArgumentException{
        int i = 0;

        /*
        for(ViewControllerMessage arg : msg){
            if(arg.getMessageID() == getStringAndMessageExpected().get(i).getMessageID()) {
                //handle(arg);
            }
            else {
                //TODO controlla eccezione
                throw new IllegalArgumentException("Sequence of messages is incorrect");
            }
        }
        */

        //modify the model
        //TODO quando avrai implementato Cell, sistemami!!

        //Cell newCell = getCellByCoordinate(newCooordinateX, newCoordinateY);
        //author.setCellPosition(newCell);


    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {

        return super.getStringAndMessageExpected();
    }

    @Override
    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {
        return super.verifyCorrectMessages(author, msg);
    }



    @Override
    public void addCell(int x, int y) throws IllegalArgumentException {
        //TODO
        newCoordinateX = x;
        newCoordinateY = y;
    }

    @Override
    public void addPlayerTarget(int playerID) throws IllegalArgumentException {

    }

    @Override
    public void addTargetingScope(int targetingCardID) throws NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {

    }

    @Override
    public void addReload(int weaponID) throws IllegalArgumentException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {

    }

    @Override
    public void addWeapon(int weaponID) throws IllegalArgumentException {

    }

    @Override
    public void addFiremode(int firemodeID) throws IllegalArgumentException {

    }
}
