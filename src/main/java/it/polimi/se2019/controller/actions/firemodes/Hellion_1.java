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
public class Hellion_1 extends FireMode {
    private static final long serialVersionUID = -2441699796090988563L;

    //messages
    static final String PLAYER_OF_CELL_REQUEST = "Select a target player. ";

    //errors
    static final String INVALID_TARGET = "You can't select this target. ";

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        StringAndMessage firstMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE, PLAYER_OF_CELL_REQUEST);
        List<StringAndMessage> list = new ArrayList<>();
        list.add(firstMessage);
        return list;
    }

    @Override
    public void fire() throws WrongInputException{
        fireHellion(1,1,0,1);
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        Cell cell = target.getCell();
        int distance = gameHandler.getMap().getDistance(author.getCell(), cell);
        if(distance > 0 && target.isVisibleBy(gameHandler.getMap(), author)){
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


    /**
     * fire for Hellion firemodes
     * @param numDamageFirst num of damage for the first target
     * @param numMarksFirst num of marks for the first target
     * @param numDamageOthers num of damage for second and others targets
     * @param numMarksOthers num of marks for second and others targets
     * @throws WrongInputException if it can't do fire
     */
    protected void fireHellion(int numDamageFirst, int numMarksFirst, int numDamageOthers, int numMarksOthers) throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException(CANT_DO_FIRE);
        }
        else{
            for(Player target : shoot.getTargetsPlayer()){
                if(shoot.getTargetsPlayer().indexOf(target) == 0){  //the first one is the primary target
                    addDamageAndMarks(target,numDamageFirst,numMarksFirst,true);
                }
                else {
                    addDamageAndMarks(target,numDamageOthers,numMarksOthers,false);
                }
            }
            commonEndingFire();
        }
    }

}
