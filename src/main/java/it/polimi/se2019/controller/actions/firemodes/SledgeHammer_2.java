package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Galluccio
 */
public class SledgeHammer_2 extends FireMode {

    private static final long serialVersionUID = -1552695057611153522L;
    private final String SELECT_PLAYER = "Select a player on your cell. ";
    private final String SELECT_CELL = "Select a cell where to move your enemy. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE, SELECT_CELL));
        return list;
    }


    @Override
    public List<AmmoBag> costAdditionalForFiremodeDuringShoot() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(1,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty() || shoot.getTargetsCells().isEmpty())
            throw new WrongInputException(CANT_DO_FIRE);

        Cell cell = shoot.getTargetsCells().get(0);
        Player target = shoot.getTargetsPlayer().get(0);
        addDamageAndMarks(target, 3, 0, true);
        if(!target.getCell().equals(cell)) target.setPosition(cell);
        super.fire();
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        Player targetPlayer = shoot.getTargetsPlayer().get(0);
        try {
            Cell cell = gameHandler.getCellByCoordinate(x, y);
            Cell targetCell = targetPlayer.getCell();
            if(gameHandler.getMap().getDistance(targetCell, cell)<3 &&
                    (gameHandler.getMap().getCellInDirection(cell, 'N').contains(targetCell) ||
                            gameHandler.getMap().getCellInDirection(cell, 'E').contains(targetCell) ||
                            gameHandler.getMap().getCellInDirection(cell, 'S').contains(targetCell) ||
                            gameHandler.getMap().getCellInDirection(cell, 'W').contains(targetCell)))
                shoot.addCellFromFireMode(cell);
            else throw new WrongInputException("You can move your enemy here. ");
        }  catch (NotPresentException e) {
            throw new WrongInputException(CELL_NOT_PRESENT);
        }
    }


    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player targetPlayer = gameHandler.getPlayerByID(playerID);
        if(targetPlayer.getCell().equals(author.getCell()))
            shoot.addPlayerTargetFromFireMode(targetPlayer, true);
        else throw new WrongInputException("This player is not on your cell. ");
    }
}
