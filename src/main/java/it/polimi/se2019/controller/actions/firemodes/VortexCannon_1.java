package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class VortexCannon_1 extends FireMode {
    //COSTS
    private static final AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(1,0,0);
    private static final long serialVersionUID = 826096278509667369L;

    //messages
    public static final String SELECT_CELL_VORTEX = "Select a cell for vortex. ";
    public static final String SELECT_FIRST_TARGET_VORTEX = "Select a player target near the vortex. ";
    public static final String SELECT_SECOND_TARGET_VORTEX_OPTIONAL = "Select a second target player. ";
    public static final String SELECT_THIRD_TARGET_VORTEX_OPTIONAL = "Select a third target player. ";


    //errors
    public static final String CELL_NOT_VISIBLE = "This cell is not visible. ";
    public static final String INVALID_TARGET_VORTEX = "This target is too distant from vortex. ";
    public static final String TOO_MANY_TARGETS = "Can't add more targets. ";
    public static final String PLAYER_ALREADY_SELECTED = "This player is already selected. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE,SELECT_CELL_VORTEX));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE,SELECT_FIRST_TARGET_VORTEX));
        return list;
    }


    @Override
    public List<AmmoBag> costAdditionalForFiremodeDuringShoot() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        list.add(COST_FIRST_OPTIONAL);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                if(target == shoot.getTargetsPlayer().get(0)){
                    addDamageAndMarks(target,2,0,true);
                }
                else{
                    addDamageAndMarks(target,1,0,true);
                }
            }
            super.fire();
        }

    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        try {
            Cell targetCell = gameHandler.getCellByCoordinate(x,y);
            if(!gameHandler.getMap().canSee(author.getCell(), targetCell)){
                throw new WrongInputException(CELL_NOT_VISIBLE);
            }
            else{
                shoot.addCellFromFireMode(targetCell);
            }
        } catch (NotPresentException e) {
            throw new WrongInputException(CELL_NOT_PRESENT);
        }


    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(target.getID() == author.getID()){
            throw new WrongInputException(SELECTED_YOURSELF);
        }
        else if(gameHandler.getMap().getDistance(shoot.getTargetsCells().get(0), target.getCell()) < 2){
            if(shoot.getTargetsPlayer().isEmpty()){
                shoot.addPlayerTargetFromFireMode(target, true);
                target.setPosition(shoot.getTargetsCells().get(0)); //target is moved to the vortex
            }
            else if(shoot.getTargetsPlayer().contains(target)){
                throw new WrongInputException(PLAYER_ALREADY_SELECTED);
            }
            else if(shoot.getTargetsPlayer().size() < 3){
                if(!shoot.getOptionalSelected().isEmpty() && shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)){
                    shoot.addPlayerTargetFromFireMode(target, true);
                    target.setPosition(shoot.getTargetsCells().get(0)); //target is moved to the vortex
                }
                else{
                    throw new WrongInputException(TOO_MANY_TARGETS);
                }
            }
            else{
                throw new WrongInputException(TOO_MANY_TARGETS);
            }
        }
        else{
            throw new WrongInputException(INVALID_TARGET_VORTEX);
        }


    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        if(numOptional == Identificator.FIRST_OPTIONAL && shoot.getOptionalSelected().isEmpty()
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_FIRST_OPTIONAL))){
            shoot.addOptionalSelected(numOptional);
            List<StringAndMessage> list = new ArrayList<>();
            list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE,SELECT_SECOND_TARGET_VORTEX_OPTIONAL));
            list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE,SELECT_THIRD_TARGET_VORTEX_OPTIONAL));
            controller.addMessageListImmediateNext(list);
            shoot.addCost(COST_FIRST_OPTIONAL);
        }
        else{
            throw new WrongInputException(CANT_DO);
        }
    }


}
