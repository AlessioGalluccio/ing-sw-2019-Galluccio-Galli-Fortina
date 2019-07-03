package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class ShotGun_1 extends FireMode {

    private static final long serialVersionUID = 5078758035082132955L;

    //messages
    static final String SELECT_PLAYER_IN_YOUR_CELL = "Select a target in your cell. ";
    static final String SELECT_CELL_TO_MOVE = "Select a cell where to move the target or fire. ";

    //errors
    static final String INVALID_CELL = "Invalid cell. ";
    static final String TOO_DISTANT_TARGET = "Target too distant. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER_IN_YOUR_CELL));
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE, SELECT_CELL_TO_MOVE));
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            addDamageAndMarks(shoot.getTargetsPlayer().get(0),3,0,true);
            super.fire();
        }
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        try {
            Cell cellTarget = gameHandler.getCellByCoordinate(x,y);
            if(gameHandler.getMap().getDistance(author.getCell(), cellTarget) == 1){
                shoot.getTargetsPlayer().get(0).setPosition(cellTarget);
            }
            else{
                throw new WrongInputException(INVALID_CELL);
            }
        } catch (NotPresentException e) {
            throw new WrongInputException(CELL_NOT_PRESENT);
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(target.getCell().equals(author.getCell()) && playerID != author.getID()){
            shoot.addPlayerTargetFromFireMode(target,true);
        }
        else{
            throw new WrongInputException(TOO_DISTANT_TARGET);
        }
    }


}
