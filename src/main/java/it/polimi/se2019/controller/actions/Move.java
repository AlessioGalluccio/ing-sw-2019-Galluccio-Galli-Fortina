package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class Move extends Action {

    private Cell cellObjective;
    private final static int DISTANCE_MAX = 3;
    private final static StringAndMessage FIRST_STRING_AND_MESS = new StringAndMessage(Identificator.CELL_MESSAGE, "Select a Cell");

    public Move(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);

    }


    public void executeAction() throws WrongInputException{
        playerAuthor.setPosition(cellObjective);
    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        ArrayList<StringAndMessage> list = new ArrayList<>();
        list.add(FIRST_STRING_AND_MESS);
        return list;
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        //TODO discutere sull'executeAction()
        List<Cell> arrayCell = gameHandler.getMap().getCellAtDistance(playerAuthor.getCell(), getMaxDistance());

        try {
            if(arrayCell.contains(gameHandler.getCellByCoordinate(x,y))) {
                cellObjective = gameHandler.getCellByCoordinate(x,y);
                executeAction();
            }
            else{
                throw new WrongInputException();
            }
        } catch (NotPresentException e) {
            //should not happen
            throw new WrongInputException();
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new WrongInputException();
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        throw new WrongInputException();
    }

    @Override
    public void addFireMode(int fireModeID) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addWeapon(WeaponCard weaponCard) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public AmmoBag getCost() {
        return new AmmoBag(0,0,0);
    }

    @Override
    public void fire() throws WrongInputException {
        throw new WrongInputException();
    }

    protected int getMaxDistance(){
        return DISTANCE_MAX;
    }
}
