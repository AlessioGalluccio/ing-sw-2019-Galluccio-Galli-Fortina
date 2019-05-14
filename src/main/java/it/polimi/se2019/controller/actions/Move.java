package it.polimi.se2019.controller.actions;


import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.CellMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class Move extends Action {

    private Cell cellObjective;
    private final static int DISTANCE_MAX = 3;

    public Move(GameHandler gameHandler) {
        super(gameHandler);
    }

    @Override
    public void executeAction() throws IllegalArgumentException{
        playerAuthor.setPosition(cellObjective);
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
        List<Cell> arrayCell = gameHandler.getMap().getCellAtDistance(playerAuthor.getCell(), DISTANCE_MAX);

        if(arrayCell.contains(gameHandler.getCellByCoordinate(x,y))) {
            cellObjective = gameHandler.getCellByCoordinate(x,y);
            executeAction();
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    @Override
    public void addTargetingScope(int targetingCardID) throws NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new IllegalArgumentException();
    }

    @Override
    public void addReload(int weaponID) throws IllegalArgumentException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        throw new IllegalArgumentException();
    }

    @Override
    public void addWeapon(int weaponID) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    @Override
    public void addFiremode(int firemodeID) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }
}
