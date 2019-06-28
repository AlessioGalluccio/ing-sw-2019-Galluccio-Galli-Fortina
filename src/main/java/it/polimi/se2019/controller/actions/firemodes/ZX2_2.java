package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class ZX2_2 extends FireMode {
    private static final long serialVersionUID = -1615539718713764664L;
    //messages
    static final String SELECT_PLAYER1 = "Select first player of max 3. ";
    static final String SELECT_PLAYER2 = "Select second player of max 3. ";
    static final String SELECT_PLAYER3 = "Select third player of max 3. ";

    //errors
    static final String WRONG_TARGET = "You can't shoot that player. ";
    static final String PLAYER_ALREADY_SELECTED = "You have already selected this player. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> list = new ArrayList<>();
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER1));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER2));
        list.add(new StringAndMessage(Identificator.PLAYER_MESSAGE, SELECT_PLAYER3));
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        if(!shoot.getTargetsPlayer().isEmpty()){
            for(Player target : shoot.getTargetsPlayer()){
                addDamageAndMarks(target,0,1, true);
            }
            super.fire();
        }
        else{
            throw new WrongInputException(CANT_DO_FIRE);
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(!target.isVisibleBy(gameHandler.getMap(), author) || target.getID() == author.getID()){
            throw new WrongInputException(WRONG_TARGET);
        }

        if(shoot.getTargetsPlayer().contains(target)){
            throw new WrongInputException(PLAYER_ALREADY_SELECTED);
        }
        else if(shoot.getTargetsPlayer().size() < 3){
            shoot.addPlayerTargetFromFireMode(target, false);
        }
        else {
            throw new WrongInputException(WRONG_TARGET);
        }

    }

    //THIS MUST REMAIN, IT LAUNCHES ANOTHER EXCEPTION COMPARED TO ZX2_1
    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new FiremodeOfOnlyMarksException();
    }

}
