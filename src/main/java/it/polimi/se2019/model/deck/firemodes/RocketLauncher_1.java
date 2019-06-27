package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class RocketLauncher_1 extends FireMode {
    private boolean nextCellIsForOptional1 = false; //true if next cell message is for optional 1

    //COSTS
    private static final AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(0,0,1);
    private static final AmmoBag COST_SECOND_OPTIONAL = new AmmoBag(0,1,0);
    private static final long serialVersionUID = -6606954983898981384L;

    //messages
    public static final String PLAYER_VISIBLE_NOT_AUTHOR_CELL = "Select a visible player not in your cell. ";
    public static final String SELECT_CELL_TO_MOVE_TARGET = "Select a cell where to move the target. ";
    public static final String SELECT_CELL_TO_MOVE_YOURSELF = "Select a cell where to move yourself. ";

    //errors
    public static final String TOO_DISTANT_CELL_YOURSELF = "This cell is too distant from you. ";
    public static final String TOO_DISTANT_CELL_TARGET = "This cell is too distant from the target. ";
    public static final String SELECT_TARGET_BEFORE = "Select a target before. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, PLAYER_VISIBLE_NOT_AUTHOR_CELL));
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE, SELECT_CELL_TO_MOVE_TARGET));
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                //first target and no second optional
                if(shoot.getTargetsPlayer().indexOf(target) == 0 &&
                        (shoot.getOptionalSelected().isEmpty() || !shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL))){
                    addDamageAndMarks(target,2,0, true);
                }
                //first target and second optional
                else if(shoot.getTargetsPlayer().indexOf(target) == 0 &&
                        !shoot.getOptionalSelected().isEmpty() && shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)){
                    addDamageAndMarks(target,3,0,true);
                }
                //other players in the cell
                else{
                    addDamageAndMarks(target,1,0,true);
                }
            }
        }
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
    public void addCell(int x, int y) throws WrongInputException {
        //optional 1 to move the shooter by a maximum of 2 moves
        if(nextCellIsForOptional1){
            try {
                Cell targetCell = gameHandler.getCellByCoordinate(x,y);
                if(gameHandler.getMap().getDistance(author.getCell(), targetCell) <= 2){
                    author.setPosition(targetCell);
                    nextCellIsForOptional1 = false;
                }
                else {
                    throw new WrongInputException(TOO_DISTANT_CELL_YOURSELF);
                }
            } catch (NotPresentException e) {
                throw new WrongInputException(CELL_NOT_PRESENT);
            }
        }
        //fore base firemode
        else{
            try {
                Cell targetCell = gameHandler.getCellByCoordinate(x,y);
                if(gameHandler.getMap().getDistance(shoot.getTargetsPlayer().get(0).getCell(), targetCell) <= 1){
                    //first target is already selected if we are here
                    shoot.getTargetsPlayer().get(0).setPosition(targetCell);
                }
                else {
                    throw new WrongInputException(TOO_DISTANT_CELL_TARGET);
                }
            } catch (NotPresentException e) {
                throw new WrongInputException(CELL_NOT_PRESENT);
            }
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        //if the player is visible, we add him and his cell to shoot. The cell will be useful in optional 2,
        //because we must use the originary poition of the target. We can move him before using optional, in fact
        Player target = gameHandler.getPlayerByID(playerID);
        if(target.isVisibleBy(gameHandler.getMap(), author)){
            shoot.addPlayerTargetFromFireMode(target,true);
            shoot.addCellFromFireMode(target.getCell());
        }
        else{
            throw new WrongInputException(NOT_VISIBLE);
        }

    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        if(numOptional == Identificator.FIRST_OPTIONAL &&
                (shoot.getOptionalSelected().isEmpty() || !shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_FIRST_OPTIONAL)))){

                shoot.addOptionalSelected(numOptional);
                StringAndMessage stringAndMessage = new StringAndMessage(Identificator.CELL_MESSAGE, SELECT_CELL_TO_MOVE_YOURSELF);
                controller.addMessageListImmediateNext(stringAndMessage);
                nextCellIsForOptional1 = true;
                shoot.addCost(COST_FIRST_OPTIONAL);
            }
        else if(numOptional == Identificator.SECOND_OPTIONAL &&
                (shoot.getOptionalSelected().isEmpty() || !shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_SECOND_OPTIONAL)))){
            if(shoot.getTargetsPlayer().isEmpty()){
                throw new WrongInputException(SELECT_TARGET_BEFORE);
            }
            else{
                shoot.addOptionalSelected(numOptional);
                for(Player target : shoot.getTargetsCells().get(0).getPlayerHere()){
                    shoot.addPlayerTargetFromFireMode(target,true);
                }
                shoot.addCost(COST_SECOND_OPTIONAL);

            }

        }
        else{
            throw new WrongInputException(CANT_DO);
        }
    }


}
