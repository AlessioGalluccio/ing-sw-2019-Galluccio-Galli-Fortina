package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class PowerGlove_1 extends FireMode {
    static final String SELECT_PLAYER = "Select a player on each cell near you. ";
    static final String WRONG_PLAYER = "You can't select that player. ";

    private static final long serialVersionUID = -3228158856128568861L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER));
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
        return list;
    }

    @Override
    public void fire() throws WrongInputException {
        Map map = gameHandler.getMap();
        if(shoot.getTargetsPlayer().isEmpty()) throw new WrongInputException(CANT_DO_FIRE);

        //Must have one target for each cell
        int oneForEach = 0;
        for(Cell cell : map.getCellAtDistance(author.getCell(), 1)) {
            if(!cell.getPlayerHere().isEmpty() && !cell.equals(author.getCell())) {
                oneForEach++;
                for(Player target : shoot.getTargetsPlayer()) {
                    if(cell.equals(target.getCell())) oneForEach--;
                }
            }
        }
        if(oneForEach > 0) throw new WrongInputException(CANT_DO_FIRE);
        for(Player target : shoot.getTargetsPlayer()) {
            addDamageAndMarks(target, 1,2,false);
        }

        //Move author on the cell of the last target
        author.setPosition(shoot.getTargetsPlayer().get(shoot.getTargetsPlayer().size()-1).getCell());
        super.fire();
    }


    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Map map = gameHandler.getMap();

        if(playerID == author.getID()) throw new WrongInputException(FireMode.SELECTED_YOURSELF);

        Player targetPlayer = gameHandler.getPlayerByID(playerID);

        //Too distant or too near
        if(map.getDistance(author.getCell(), targetPlayer.getCell())!=1)
            throw new WrongInputException(WRONG_PLAYER);

        //Two player on the same cell
        for(Player target : shoot.getTargetsPlayer()) {
            if(target.getCell().equals(targetPlayer.getCell()))
                throw new WrongInputException(WRONG_PLAYER);
        }

        shoot.addPlayerTargetFromFireMode(targetPlayer, true);
    }

}
