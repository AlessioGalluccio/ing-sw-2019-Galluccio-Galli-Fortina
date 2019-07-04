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
public class Thor_1 extends FireMode {

    //COSTS
    private static final AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(0,0,1);
    private static final AmmoBag COST_SECOND_OPTIONAL = new AmmoBag(0,0,1);
    private static final long serialVersionUID = 5449929047676709791L;

    //messages
    static final String SELECT_TARGET_NORMAL = "Select a player target you can see. ";
    static final String SELECT_TARGET_OPTIONAL_1 = "Select a player target whom the first target can see. ";
    static final String SELECT_TARGET_OPTIONAL_2 = "Select a player target whom the second target can see. ";

    //errors
    static final String NOT_VISIBLE_BY_AUTHOR = "This target is not visible by author. ";
    static final String NOT_VISIBLE_BY_FIRST_TARGET= "This target is not visible by first target. ";
    static final String NOT_VISIBLE_BY_SECOND_TARGET= "This target is not visible by second target. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_TARGET_NORMAL));
        return list;
    }


    @Override
    public List<AmmoBag> costAdditionalForFiremodeDuringShoot() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        list.add(COST_FIRST_OPTIONAL);
        list.add(COST_SECOND_OPTIONAL);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                //if it's the second target, only one damage
                if(shoot.getTargetsPlayer().indexOf(target) == 1){
                    addDamageAndMarks(target,1,0, true);
                }
                else{
                    addDamageAndMarks(target,2,0,true);
                }
            }
            super.fire();
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(shoot.getTargetsPlayer().isEmpty()){
            if(target.isVisibleBy(gameHandler.getMap(),author)){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
            else{
                throw new WrongInputException(NOT_VISIBLE_BY_AUTHOR);
            }
        }
        else if(shoot.getTargetsPlayer().size() == 1 && !shoot.getTargetsPlayer().contains(target)){
            //must be visible ny the first target player
            if(target.isVisibleBy(gameHandler.getMap(),shoot.getTargetsPlayer().get(0))){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
            else{
                throw new WrongInputException(NOT_VISIBLE_BY_FIRST_TARGET);
            }
        }
        else if(shoot.getTargetsPlayer().size() == 2 && !shoot.getTargetsPlayer().contains(target)){
            //must be visible ny the second target player
            if(target.isVisibleBy(gameHandler.getMap(),shoot.getTargetsPlayer().get(1))){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
            else{
                throw new WrongInputException(NOT_VISIBLE_BY_SECOND_TARGET);
            }
        }
        else{
            throw new WrongInputException(CANT_DO);
        }

    }


    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        if(numOptional == Identificator.FIRST_OPTIONAL && shoot.getOptionalSelected().isEmpty()
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_FIRST_OPTIONAL))){
            shoot.addOptionalFromFiremode(numOptional);
            StringAndMessage stringAndMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                    SELECT_TARGET_OPTIONAL_1);
            controller.addMessageListImmediateNext(stringAndMessage);
            shoot.addCost(COST_FIRST_OPTIONAL);


        }
        //it must contain the first optional for the second optional
        else if(numOptional == Identificator.SECOND_OPTIONAL &&
                (!shoot.getOptionalSelected().isEmpty() || shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL))
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_SECOND_OPTIONAL))) {
            shoot.addOptionalFromFiremode(numOptional);
            StringAndMessage stringAndMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                    SELECT_TARGET_OPTIONAL_2);
            controller.addMessageListImmediateNext(stringAndMessage);
            shoot.addCost(COST_SECOND_OPTIONAL);
        }

        else{
            throw new WrongInputException(CANT_DO);
        }
    }


}
