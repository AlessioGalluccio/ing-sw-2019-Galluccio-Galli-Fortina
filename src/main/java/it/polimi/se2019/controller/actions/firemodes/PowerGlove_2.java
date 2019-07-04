package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galli
 * @author Galluccio
 */
public class PowerGlove_2 extends FireMode {
    private static final long serialVersionUID = 4773017297111698934L;
    static final String SELECT_PLAYER = "Select a player on that cell. ";
    static final String SELECT_CELL = "Select a cell one move away. ";
    static final String SELECT_CELL_2 = "Select a another cell one move away. ";
    static final String WRONG_CELL = "You can't select that cell. ";
    static final String WRONG_PLAYER = "You can't select that player. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE, SELECT_CELL));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE, SELECT_CELL_2));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
        return list;
    }

    @Override
    public List<AmmoBag> costAdditionalForFiremodeDuringShoot() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,1)); //cost of shooting base firemode
        return list;
    }


    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsCells().isEmpty() ){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player targetPlayer : shoot.getTargetsPlayer()) {
                addDamageAndMarks(targetPlayer, 2,0,true);
            }
            author.setPosition(shoot.getTargetsCells().get(shoot.getTargetsCells().size()-1));
            super.fire();
        }
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        Map map = gameHandler.getMap();
        try {
            Cell target = gameHandler.getCellByCoordinate(x,y);
            //Too distant or too near
            if(shoot.getTargetsCells().isEmpty()) {
                if(map.getDistance(author.getCell(), target)!=1)
                    throw new WrongInputException(WRONG_CELL);
            } else{
                if(map.getDistance(author.getCell(), shoot.getTargetsCells().get(0))!=1) {
                    throw new WrongInputException(WRONG_CELL);
                    //Distant 1 but not same direction
                } else if(!sameDirection(target)) throw new WrongInputException(WRONG_CELL);
            }
            shoot.addCellFromFireMode(target);
        } catch (NotPresentException e) {
            throw new WrongInputException(FireMode.CELL_NOT_PRESENT);
        }
    }

    private boolean sameDirection(Cell target) {
        Map map = gameHandler.getMap();
        Cell cell = shoot.getTargetsCells().get(0);
        char[] NESW = {'N', 'E', 'S', 'W'};
        for(char direction : NESW) {
            List<Cell> cells = map.getCellInDirection(author.getCell(), direction);
            if(cells.contains(cell) && cells.contains(target)) return true;
        }
        return false;
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        if(playerID==author.getID()) throw new WrongInputException(SELECTED_YOURSELF);

        Player target = gameHandler.getPlayerByID(playerID);
        if(shoot.getTargetsCells().size()==1 && shoot.getTargetsCells().get(0).equals(target.getCell()))
            shoot.addPlayerTargetFromFireMode(target, true);

        else if(shoot.getTargetsCells().size()==2 && shoot.getTargetsCells().get(1).equals(target.getCell()))
            shoot.addPlayerTargetFromFireMode(target, true);
        else throw new WrongInputException("Can't select this target. ");
    }

}
