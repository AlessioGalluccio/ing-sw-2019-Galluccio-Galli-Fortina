package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class RailGun_2 extends RailGun_1 {

    private static final long serialVersionUID = 3954418059011118447L;

    public static final String PLAYER_ALREADY_SELECTED = "This player is already selected. ";
    public static final String SELECT_SECOND_PLAYER = "Select another target player or fire. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER_RAILGUN));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_SECOND_PLAYER));
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target, 2, 0, true);
            }
        }
        commonEndingFire();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        if(!shoot.getTargetsPlayer().contains(gameHandler.getPlayerByID(playerID))){
            super.addPlayerTarget(playerID);
        }
        else{
            throw new WrongInputException(PLAYER_ALREADY_SELECTED);
        }
    }

    @Override
    protected boolean isInValidPosition(Cell cellOfTarget){
        return (super.isInValidPosition(cellOfTarget) &&
                (cellOfTarget.getCoordinateX() == author.getCell().getCoordinateX()
                        || cellOfTarget.getCoordinateY() == author.getCell().getCoordinateY()));
    }

}
