package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class ShotGun_2 extends FireMode {

    private static final long serialVersionUID = 1155806501696304392L;

    //messages
    public static final String SELECT_PLAYER_DISTANT_ONE = "Select a target distant one cell. ";


    //errors
    public static final String INVALID_TARGET_SHOTGUN2 = "Invalid target. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER_DISTANT_ONE));
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            addDamageAndMarks(shoot.getTargetsPlayer().get(0),2,0,true);
            super.fire();
        }
    }


    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(gameHandler.getMap().getDistance(author.getCell(),target.getCell()) == 1){
            shoot.addPlayerTargetFromFireMode(target,true);
        }
        else{
            throw new WrongInputException(INVALID_TARGET_SHOTGUN2);
        }
    }

}
