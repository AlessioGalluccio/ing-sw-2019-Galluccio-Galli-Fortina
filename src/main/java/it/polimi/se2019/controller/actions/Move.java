package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.CellMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class Move extends Action {

    private Cell cellObjective;
    private final static int DISTANCE_MAX = 3;
    private final static StringAndMessage FIRST_STRING_AND_MESS = new StringAndMessage(Identificator.CELL_MESSAGE, "Select a Cell", false);

    public Move(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
        correctMessages.add(FIRST_STRING_AND_MESS);

    }

    @Override
    public void executeAction() throws WrongInputException{
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
    public void addCell(int x, int y) throws WrongInputException {
        //TODO discutere sull'executeAction()
        List<Cell> arrayCell = gameHandler.getMap().getCellAtDistance(playerAuthor.getCell(), DISTANCE_MAX);

        if(arrayCell.contains(gameHandler.getCellByCoordinate(x,y))) {
            cellObjective = gameHandler.getCellByCoordinate(x,y);
            executeAction();
        }
        else{
            throw new WrongInputException();
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addTargetingScope(int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new WrongInputException();
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        throw new WrongInputException();
    }

    @Override
    public void addWeapon(int weaponID) throws WrongInputException {
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
    public void addNope() throws WrongInputException {
        throw new WrongInputException();
    }
}
