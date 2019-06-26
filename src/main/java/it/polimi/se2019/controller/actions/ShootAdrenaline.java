package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;


public class ShootAdrenaline extends Shoot {

    private int DISTANCE_MAX = 1;
    //messages
    public static final String CHOOSE_CELL_ADRENALINE_SHOOT ="Select a cell. ";
    //errors
    public static final String TOO_MUCH_DISTANCE = "This cell is too distant. ";
    public static final String CELL_NOT_PRESENT = "This cell is not present. ";

    public ShootAdrenaline(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.CELL_MESSAGE, CHOOSE_CELL_ADRENALINE_SHOOT);
        StringAndMessage secondMessage = new StringAndMessage(Identificator.WEAPON_MESSAGE, Shoot.FIRST_MESSAGE);
        StringAndMessage thirdMessage = new StringAndMessage(Identificator.FIRE_MODE_MESSAGE, Shoot.SECOND_MESSAGE);
        StringAndMessage lastMessage = new StringAndMessage(Identificator.FIRE_MESSAGE, Shoot.LAST_MESSAGE);

        ArrayList<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        list.add(secondMessage);
        list.add(thirdMessage);
        list.add(lastMessage);
        return list;
    }

    @Override
    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {
        return super.verifyCorrectMessages(author, msg);
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        if (fireMode == null) {
            try {
                Cell cell = gameHandler.getCellByCoordinate(x,y);
                if(gameHandler.getMap().getDistance(cell, playerAuthor.getCell()) > getMaxDistance()){
                    throw new WrongInputException(TOO_MUCH_DISTANCE);
                }
                else{
                    playerAuthor.setPosition(cell);
                }
            } catch (NotPresentException e) {
                throw new WrongInputException(CELL_NOT_PRESENT);
            }

        } else {
            fireMode.addCell(x, y);
        }

    }

    protected int getMaxDistance(){
        return DISTANCE_MAX;
    }
}
