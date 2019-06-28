package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class HeatSeeker_1 extends FireMode {

    private static final long serialVersionUID = 4760119110368705474L;
    private static String FIRST_MSG_STR = "Select a player target. ";

    //messsages
    public static final String SELECT_TARGET_NOT_VISIBLE = "Select a target not visible. ";

    //errors
    public static final String TARGET_NOT_VALID = "This target is not valid. ";


    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMsg = new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_TARGET_NOT_VISIBLE);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMsg);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().size() == 1){
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 3,0, true);
            super.fire();
        }
        else{
            throw new WrongInputException(CANT_DO_FIRE);
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player targetPlayer = gameHandler.getPlayerByID(playerID);
        if(shoot.getTargetsPlayer().isEmpty() && !targetPlayer.isVisibleBy(gameHandler.getMap(), author)){
            shoot.addPlayerTargetFromFireMode(targetPlayer, true);
        }
        else {
            throw new WrongInputException(TARGET_NOT_VALID);
        }

    }
}
