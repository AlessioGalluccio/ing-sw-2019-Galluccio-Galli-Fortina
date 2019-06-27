package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class CyberBlade_1 extends FireMode {
    private static final long serialVersionUID = 6386804975541460018L;
    private AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(0,0,0);
    private AmmoBag COST_SECOND_OPTIONAL = new AmmoBag(0,1,0);

    public static final String FIRST_MESSAGE = "Select a player target. ";
    public static final String FIRST_OPTIONAL_MESSAGE = "Select a cell where to move. ";
    public static final String SECOND_OPTIONAL_MESSAGE = "Select a second player target. ";

    public static final String SELECT_FIRST_TARGET_BEFORE = "Select a target for the normal firemode before.";
    public static final String OPTIONAL_ALREADY_SELECTED = "Optional already selected.";
    public static final String TOO_MUCH_DISTANCE = "This cell is too distant.";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, FIRST_MESSAGE);
        list.add(firstMessage);
        return list;
    }


    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        list.add(COST_FIRST_OPTIONAL);
        list.add(COST_SECOND_OPTIONAL);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsPlayer().isEmpty()){
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target,2,0, true);
            }
            super.fire();
        }
        else{
            throw new WrongInputException(CANT_DO);
        }
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        try {
            Cell cellDestination = gameHandler.getCellByCoordinate(x,y);
            int distance = gameHandler.getMap().getDistance(author.getCell(), cellDestination);
            if(distance <= 1){
                author.setPosition(cellDestination);
            }
            else {
                throw new WrongInputException(TOO_MUCH_DISTANCE);
            }
        } catch (NotPresentException e) {
            throw new WrongInputException(CELL_NOT_PRESENT);
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(shoot.getTargetsPlayer().isEmpty() && playerID != author.getID() &&
                target.getCell().equals(author.getCell())){
            shoot.addPlayerTargetFromFireMode(target, true);
        }
        else if(!shoot.getTargetsPlayer().isEmpty() && shoot.getTargetsPlayer().size() == 1
                && shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)
                && target.getCell().equals(author.getCell())
                && playerID != shoot.getTargetsPlayer().get(0).getID()){
            shoot.addPlayerTargetFromFireMode(target, true);

        }
        else{
            throw new WrongInputException(CANT_DO);
        }
    }


    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        if(shoot.getOptionalSelected().contains(numOptional)){
            throw new WrongInputException(OPTIONAL_ALREADY_SELECTED);
        }
        else{
            if(numOptional == Identificator.FIRST_OPTIONAL
                    && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_FIRST_OPTIONAL))){
                shoot.addOptionalSelected(numOptional);
                StringAndMessage stringAndMessage = new StringAndMessage(Identificator.CELL_MESSAGE,
                        FIRST_OPTIONAL_MESSAGE);
                controller.addMessageListImmediateNext(stringAndMessage);
                shoot.addCost(COST_FIRST_OPTIONAL);
            }
            else if(numOptional == Identificator.SECOND_OPTIONAL &&
                    author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_SECOND_OPTIONAL))){
                ArrayList<Player> possibleTargets = new ArrayList<>();
                for(Player target : author.getCell().getPlayerHere()){
                    if(target.getID() != author.getID() && !shoot.getTargetsPlayer().isEmpty()
                            && target.getID() != shoot.getTargetsPlayer().get(0).getID()){
                        possibleTargets.add(target);
                    }
                }
                if(shoot.getTargetsPlayer().isEmpty()){
                    throw new WrongInputException(SELECT_FIRST_TARGET_BEFORE);
                }
                else if(!shoot.getTargetsPlayer().isEmpty() && !possibleTargets.isEmpty()){
                    shoot.addOptionalSelected(numOptional);
                    StringAndMessage stringAndMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                            SECOND_OPTIONAL_MESSAGE);
                    controller.addMessageListImmediateNext(stringAndMessage);
                    shoot.addCost(COST_SECOND_OPTIONAL);
                }
                else{
                    throw new WrongInputException(CANT_DO);
                }

            }
            else{
                throw new WrongInputException(CANT_DO);
            }
        }
    }

}
