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
public class TractorBeam_1 extends FireMode {

    private static final long serialVersionUID = 4282563996993084174L;
    private final String TOO_DISTANT = "The target you chose is too distant from you.\n";
    private final String SELECT_PLAYER = "Select a player, even if you can't see it. ";
    private final String SELECT_CELL = "Select a cell where to move the target. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        list.add(new StringAndMessage(Identificator.CELL_MESSAGE, SELECT_CELL));
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty() || shoot.getTargetsCells().isEmpty())
            throw new WrongInputException(CANT_DO_FIRE);

        Cell cell = shoot.getTargetsCells().get(0);
        Player target = shoot.getTargetsPlayer().get(0);
        if(!target.getCell().equals(cell)) target.setPosition(cell);
        addDamageAndMarks(target, 1, 0, true);
        super.fire();
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        if(shoot.getTargetsPlayer().isEmpty()) throw new WrongInputException(SELECT_PLAYER);
        try {
            Player targetPlayer = shoot.getTargetsPlayer().get(0);
            Cell targetCell = gameHandler.getCellByCoordinate(x,y);
            if(gameHandler.getMap().canSee(author.getCell(), targetCell) &&
            gameHandler.getMap().getCellAtDistance(targetPlayer.getCell(), 2).contains(targetCell))
                shoot.addCellFromFireMode(targetCell);
            else throw new WrongInputException(TOO_DISTANT);
        } catch (NotPresentException e) {
            throw new WrongInputException(CELL_NOT_PRESENT);
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player targetPlayer = gameHandler.getPlayerByID(playerID);
        Map map = gameHandler.getMap();
        for(Cell cell : map.getCellAtDistance(targetPlayer.getCell(), 2)) {
            if(map.canSee(cell, author.getCell())) {
                shoot.addPlayerTargetFromFireMode(targetPlayer, true);
                return;
            }
        }
        throw new WrongInputException(TOO_DISTANT);
    }


}
