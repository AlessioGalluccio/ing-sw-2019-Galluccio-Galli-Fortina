package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.List;

public class Hellion_1 extends FireMode {

    //messages
    public static final String PLAYER_OF_CELL_REQUEST = "Select a target player. ";

    //errors
    public static final String INVALID_TARGET = "You can't select this target. ";
    private static final long serialVersionUID = -2441699796090988563L;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, PLAYER_OF_CELL_REQUEST);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
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
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                if(shoot.getTargetsPlayer().indexOf(target) == 0){  //the first one is the primary target
                    addDamageAndMarks(target,1,1,true);
                }
                else {
                    addDamageAndMarks(target,0,1,false);
                }
            }
        }
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        Cell cell = target.getCell();
        int distance = gameHandler.getMap().getDistance(author.getCell(), cell);
        if(distance > 0){
            shoot.addPlayerTargetFromFireMode(target, true);
            for(Player secondaryTarget : target.getCell().getPlayerHere()){
                if(secondaryTarget.getID() != target.getID()){
                    shoot.addPlayerTargetFromFireMode(secondaryTarget, false); //false because we add only marks
                }

            }
        }
        else{
            throw new WrongInputException(INVALID_TARGET);
        }
    }

}
