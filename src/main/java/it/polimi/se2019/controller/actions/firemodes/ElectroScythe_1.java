package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galluccio
 */
public class ElectroScythe_1 extends FireMode {
    private static final String SEND_FIRE = "Press fire to complete the action";

    private static final String NO_VISIBLE_FOR_TARGETING = "No visible target for Targeting. ";
    private static final String INVALID_TARGET_FOR_TARGETING = "Invalid target for targeting scope. ";
    private static final long serialVersionUID = 5053678759660132677L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        for(Player target : author.getCell().getPlayerHere()){
            if(target.getID() != author.getID()){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
        }
        return new ArrayList<>(); //empty list
    }


    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target, 1,0, true);
            }
            super.fire();
        }
    }

}
