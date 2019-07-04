package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galluccio
 */
public class SledgeHammer_1 extends FireMode {

    private static final long serialVersionUID = 8298297204873304371L;

    //messages
    public static final String SELECT_PLAYER = "Select a player on your cell. ";

    //errors
    static final String NOT_ON_YOUR_CELL = "This player is not on your cell. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()) throw new WrongInputException(CANT_DO_FIRE);
        addDamageAndMarks(shoot.getTargetsPlayer().get(0), 2, 0, true);
        super.fire();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player targetPlayer = gameHandler.getPlayerByID(playerID);
        if(targetPlayer.getCell().equals(author.getCell()))
            shoot.addPlayerTargetFromFireMode(targetPlayer, true);
        else throw new WrongInputException(NOT_ON_YOUR_CELL);
    }
}
