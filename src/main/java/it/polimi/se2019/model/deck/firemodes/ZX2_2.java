package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class ZX2_2 extends FireMode {

    private static final long serialVersionUID = -1615539718713764664L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        return null;
    }

    @Override
    public void sendPossibleTargetsAtStart() {

        sendAllVisiblePlayers(null);
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
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
            throw new WrongInputException();
        }

    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        if(!target.isVisibleBy(gameHandler.getMap(), author) || target.getID() == author.getID()){
            throw new WrongInputException();
        }

        if(shoot.getTargetsPlayer().size() < 3){
            shoot.addPlayerTargetFromFireMode(target, false);
            sendAllVisiblePlayers(shoot.getTargetsPlayer());
        }
        else {
            throw new WrongInputException();
        }

    }

    //THIS MUST REMAIN, IT LAUNCHES ANOTHER EXCEPTION
    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new FiremodeOfOnlyMarksException();
    }

}
