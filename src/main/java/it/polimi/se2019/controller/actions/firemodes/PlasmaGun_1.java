package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class PlasmaGun_1 extends FireMode {

    //COSTS
    private static final AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(0,0,0);
    private static final AmmoBag COST_SECOND_OPTIONAL = new AmmoBag(0,0,1);
    private static final long serialVersionUID = 4000661290173963365L;

    //messages
    public static final String SELECT_VISIBLE_TARGET = "Select a visible target player. ";
    public static final String SELECT_CELL_TO_MOVE = "Select a cell where to move. Max 2 moves. ";

    //errors
    public static final String CELL_TOO_DISTANT_PLASMA = "This cell is too distant. ";
    public static final String NOT_VISIBLE_TARGET = "This target s not visible. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_VISIBLE_TARGET));
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
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            if(shoot.getOptionalSelected().isEmpty() || !shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)){
                //first optional is not selected
                addDamageAndMarks(shoot.getTargetsPlayer().get(0), 2,0,true);
            }
            else{
                //first optional is selected
                addDamageAndMarks(shoot.getTargetsPlayer().get(0), 3,0,true);

            }
            super.fire();
        }
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        try {
            Cell cellTarget = gameHandler.getCellByCoordinate(x,y);
            if(gameHandler.getMap().getDistance(author.getCell(), cellTarget) <= 2){
                author.setPosition(cellTarget);
            }
            else{
                throw new WrongInputException(CELL_TOO_DISTANT_PLASMA);
            }
        } catch (NotPresentException e) {
            throw new WrongInputException(CELL_NOT_PRESENT);
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(target.isVisibleBy(gameHandler.getMap(), author)){
            shoot.addPlayerTargetFromFireMode(target, true);
        }
        else{
            throw new WrongInputException(NOT_VISIBLE_TARGET);
        }
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        if(numOptional == Identificator.FIRST_OPTIONAL &&
                (shoot.getOptionalSelected().isEmpty() || !shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL))
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_FIRST_OPTIONAL))){

            shoot.addOptionalSelected(numOptional);
            StringAndMessage stringAndMessage = new StringAndMessage(Identificator.CELL_MESSAGE,
                    SELECT_CELL_TO_MOVE);
            controller.addMessageListImmediateNext(stringAndMessage);
            shoot.addCost(COST_FIRST_OPTIONAL);
        }
        else if(numOptional == Identificator.SECOND_OPTIONAL &&
                (shoot.getOptionalSelected().isEmpty() || !shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL))
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_SECOND_OPTIONAL))){
            shoot.addOptionalSelected(numOptional); //it's just for adding one more damage
            shoot.addCost(COST_SECOND_OPTIONAL);

        }
        else{
            throw new WrongInputException(CANT_DO);
        }
    }

}
