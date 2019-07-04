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

/**
 * @author Galluccio
 */
public class Move extends Action {

    private Cell cellObjective;
    private static final int DISTANCE_MAX = 3;

    //messages
    static final StringAndMessage FIRST_STRING_AND_MESS = new StringAndMessage(Identificator.CELL_MESSAGE, "Select a Cell");

    //errors
    static final String TOO_DISTANT_CELL = "This cell is too distant. ";

    /**
     * constructor
     * @param gameHandler the gamehandler of the match
     * @param controller the controller of the player
     */
    public Move(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);

    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        ArrayList<StringAndMessage> list = new ArrayList<>();
        list.add(FIRST_STRING_AND_MESS);
        return list;
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        List<Cell> arrayCell = gameHandler.getMap().getCellAtDistance(playerAuthor.getCell(), getMaxDistance());

        try {
            if(arrayCell.contains(gameHandler.getCellByCoordinate(x,y))) {
                cellObjective = gameHandler.getCellByCoordinate(x,y);
                executeAction();
            }
            else{
                throw new WrongInputException(TOO_DISTANT_CELL);
            }
        } catch (NotPresentException e) {
            //should not happen
            throw new WrongInputException();
        }
    }

    @Override
    public AmmoBag getCost() {
        return new AmmoBag(0,0,0);
    }

    /**
     * get max distance of this action Move
     * @return the in max distance
     */
    protected int getMaxDistance(){
        return DISTANCE_MAX;
    }

    /**
     * execute this action move
     */
    private void executeAction(){
        playerAuthor.setPosition(cellObjective);
    }
}
