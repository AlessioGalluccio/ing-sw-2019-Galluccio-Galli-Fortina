package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class ZX2_1 extends FireMode {
    private static final long serialVersionUID = 1525303002085550524L;
    static final String SELECT_PLAYER = "Select a player you can see it. ";
    static final String WRONG_TARGET = "You can't shoot that player. ";

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
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().size() == 1){
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 1,2, true);
            super.fire();
        }
        else{
            throw new WrongInputException(CANT_DO_FIRE);
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(target.isVisibleBy(gameHandler.getMap(), author) &&
                shoot.getTargetsPlayer().isEmpty() && target.getID() != author.getID()){
            shoot.addPlayerTargetFromFireMode(target, true);
        }
        else{
            throw new WrongInputException(WRONG_TARGET);
        }
    }


}
