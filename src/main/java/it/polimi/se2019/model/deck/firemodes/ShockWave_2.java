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

public class ShockWave_2 extends FireMode {

    private static final long serialVersionUID = 8956505216147899205L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        //no messages are needed, we only add the targets for the targeting scope
        for(Player target : gameHandler.getOrderPlayerList()){
            if(gameHandler.getMap().getDistance(author.getCell(), target.getCell()) == 1){
               shoot.addPlayerTargetFromFireMode(target, true);
            }
        }
        return new ArrayList<>(); //empty list
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,1,0)); //cost of shooting base firemode
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        for(Player target : shoot.getTargetsPlayer()){
            addDamageAndMarks(target,1,0, true);
        }
        super.fire();
    }



}
