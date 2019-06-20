package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class GrenadeLauncher_1 extends FireMode {
    private static final long serialVersionUID = -6042825291183206795L;
    private boolean nextCellIsForOptional = false;
    private AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(1,0,0);

    public static final String FIRST_MESSAGE = "Select a player from possible targets";
    //TODO controlla questo messaggio
    public static final String SECOND_MESSAGE = "Select a cell where to move the target. Select the cell where he is if you don't want to move him";
    public static final String OPTIONAL_MESSAGE = "Select a cell to launch the extra granade";

    public static final String SELECT_FIRST_TARGET_BEFORE = "Select a target for the normal firemode before.";
    public static final String TOO_MUCH_DISTANCE = "This cell is too distant.";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        ArrayList<StringAndMessage> list = new ArrayList<>();
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, FIRST_MESSAGE);
        StringAndMessage secondMessage = new StringAndMessage(Identificator.CELL_MESSAGE, SECOND_MESSAGE);
        list.add(firstMessage);
        list.add(secondMessage);
        return list;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
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
        else if(shoot.getOptionalSelected().isEmpty()){
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 1,0, true);
            super.fire();
        }
        else{
            //if first target is in the same cell, there are two instances of him in the array, so he will get 2 damage
            //the true in marks should not be a problem, because this firemode doens't add marks, so they will be expended
            //in the first addDamageAndMarks
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target,1,0, true);
            }
            super.fire();
        }
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        try {
            Cell cell = gameHandler.getCellByCoordinate(x,y);

            //add a cell for optional firemode
            if(nextCellIsForOptional){
                for(Player target : cell.getPlayerHere()){
                    //if the first target is in this cell, he will be added two times. He will get both damages
                    if(target.getID() != author.getID()){
                        shoot.addPlayerTargetFromFireMode(target, true);
                    }
                }
                nextCellIsForOptional = false;
            }
            //normal firemode, you move the target in a new position
            else{
                Player target = shoot.getTargetsPlayer().get(0);
                int distance = gameHandler.getMap().getDistance(target.getCell(), cell);
                if(distance <= 1){
                    target.setPosition(cell);
                }
                else{
                    throw new WrongInputException(TOO_MUCH_DISTANCE);
                }

            }
        } catch (NotPresentException e) {
            throw new WrongInputException(CELL_NOT_PRESENT);
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        int targetID = target.getID();
        if(targetID == author.getID()){
            throw new WrongInputException(SELECTED_YOURSELF);
        }
        else if(target.isVisibleBy(author) && shoot.getTargetsPlayer().isEmpty()){
            shoot.addPlayerTargetFromFireMode(target, true);
        }
        else{
            throw new WrongInputException(NOT_VISIBLE);
        }
    }


    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        if(numOptional == Identificator.FIRST_OPTIONAL && shoot.getOptionalSelected().isEmpty()
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_FIRST_OPTIONAL))){
            if(shoot.getTargetsPlayer().isEmpty()) {
                throw new WrongInputException(SELECT_FIRST_TARGET_BEFORE);
            }
            shoot.addOptionalSelected(numOptional);
            StringAndMessage stringAndMessage = new StringAndMessage(Identificator.CELL_MESSAGE,
                    OPTIONAL_MESSAGE);
            controller.addMessageListImmediateNext(stringAndMessage);
            shoot.addCost(COST_FIRST_OPTIONAL);

            nextCellIsForOptional = true;
        }
        else{
            throw new WrongInputException(CANT_DO);
        }
    }




}
