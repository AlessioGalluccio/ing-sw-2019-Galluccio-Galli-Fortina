package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class Furnace_2 extends FireMode {

    //messages
    public static final String ADJACENT_CELL_REQUEST = "Select an adjacent cell. ";

    //errors
    public static final String INVALID_CELL = "You can't select this cell. ";
    public static final String NO_TARGETS_IN_CELL = "There are no targets in this cell. ";
    private static final long serialVersionUID = -315490042639058980L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.CELL_MESSAGE, ADJACENT_CELL_REQUEST);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        return list;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        for(Player target : shoot.getTargetsPlayer()){
            addDamageAndMarks(target,1,1,true);
        }
        super.fire();
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
