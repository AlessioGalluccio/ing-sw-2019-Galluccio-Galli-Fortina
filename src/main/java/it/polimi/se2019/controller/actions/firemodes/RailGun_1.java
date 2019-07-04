package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galluccio
 */
public class RailGun_1 extends FireMode {

    private static final long serialVersionUID = 4684255276743354888L;

    //messages
    static final String SELECT_PLAYER_RAILGUN = "Select a target player. ";

    //errors
    static final String INVALID_TARGET = "Invalid target. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER_RAILGUN));
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 3, 0, true);
        }
        super.fire();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        if(playerID == author.getID()){
            throw new WrongInputException(SELECTED_YOURSELF);
        }
        else {
            Player target = gameHandler.getPlayerByID(playerID);
            if(isInValidPosition(target.getCell())){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
            else{
                throw new WrongInputException(INVALID_TARGET);
            }
        }
    }

    /**
     * returns true if cell target is in valid position for RailGun
     * @param cellOfTarget the target cell
     * @return true if valid target cell, false if not
     */
    protected boolean isInValidPosition(Cell cellOfTarget){
        return(gameHandler.getMap().getCellInDirection(author.getCell(),'N').contains(cellOfTarget) ||
            gameHandler.getMap().getCellInDirection(author.getCell(),'E').contains(cellOfTarget) ||
            gameHandler.getMap().getCellInDirection(author.getCell(),'S').contains(cellOfTarget) ||
            gameHandler.getMap().getCellInDirection(author.getCell(),'W').contains(cellOfTarget));
    }

}
