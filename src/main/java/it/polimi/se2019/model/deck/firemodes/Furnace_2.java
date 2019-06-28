package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class Furnace_2 extends FireMode {
    private static final long serialVersionUID = -315490042639058980L;

    //messages
    static final String ADJACENT_CELL_REQUEST = "Select an adjacent cell. ";

    //errors
    static final String INVALID_CELL = "You can't select this cell. ";
    static final String NO_TARGETS_IN_CELL = "There are no targets in this cell. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.CELL_MESSAGE, ADJACENT_CELL_REQUEST);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target,1,1,true);
            }
            super.fire();
        }

    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        try{
            Cell targetCell = gameHandler.getCellByCoordinate(x,y);
            int distance = gameHandler.getMap().getDistance(author.getCell(), targetCell);
            if(distance == 1){
                for(Player target : targetCell.getPlayerHere()){
                    shoot.addPlayerTargetFromFireMode(target,true);
                }
                //if there weren't targets in this cell, it luanches exception
                if(shoot.getTargetsPlayer().isEmpty()){
                    throw new WrongInputException(NO_TARGETS_IN_CELL);
                }
            }
            else{
                throw new WrongInputException(INVALID_CELL);
            }
        }catch (NotPresentException e){
            throw new WrongInputException(CELL_NOT_PRESENT);
        }

    }

}
