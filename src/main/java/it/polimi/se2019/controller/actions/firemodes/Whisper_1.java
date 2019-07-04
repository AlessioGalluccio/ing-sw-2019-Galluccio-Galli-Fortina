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
public class Whisper_1 extends FireMode {
    private static final long serialVersionUID = 2287199755551717645L;

    static final String SELECT_PLAYER = "Select a player you can see far enough from you. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsPlayer().isEmpty()){
            Player target = shoot.getTargetsPlayer().get(0);
            addDamageAndMarks(target, 3,1, true);
            super.fire();
        }
        else{
            throw new WrongInputException(CANT_DO_FIRE);
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        List<Cell> arrayCell = gameHandler.getMap().getCellAtDistance(author.getCell(), 1);
        if(shoot.getTargetsPlayer().isEmpty() &&
                !arrayCell.contains(target.getCell()) &&
                target.isVisibleBy(gameHandler.getMap(), author)){
            shoot.addPlayerTargetFromFireMode(target, true);
        }
        else{
            throw new WrongInputException("This player is on an invalid cell. ");
        }
    }
}
