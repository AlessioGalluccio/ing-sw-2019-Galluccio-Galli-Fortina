package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TractorBeam_2 extends FireMode {
    private static final long serialVersionUID = 5530034654912263654L;
    private final String TOO_DISTANT = "The target you chose is too distant from you.\n";
    private final String SELECT_PLAYER = "Select a player up to two cells distant from you, even if you can't see it. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        return list;
    }


    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(1,1,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().size() != 1){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        for(Player target : shoot.getTargetsPlayer()){
            target.setPosition(author.getCell());
            addDamageAndMarks(target,3,0, true);
        }
        super.fire();

    }

    //void addCell(int x, int y) call the method of the super class


    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        List<Cell> possibleCell = gameHandler.getMap().getCellAtDistance(author.getCell(), 2);
        List<Player> possibleTarget = new LinkedList<>();
        for(Cell cell : possibleCell) {
            possibleTarget.addAll(cell.getPlayerHere());
        }
        if(possibleTarget.contains(target)) shoot.addPlayerTargetFromFireMode(target, true);
        else{
            throw new WrongInputException(TOO_DISTANT);
        }
    }

    // void addOptional(int numOptional) call the method of the super class

}
