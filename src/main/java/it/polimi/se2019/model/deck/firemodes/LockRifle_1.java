package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class LockRifle_1 extends FireMode {
    private static final int NUM_DAMAGE = 2;
    private static final int NUM_MARK = 1;

    private static final AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(1,0,0);

    private static final String FIRST_MSG_STR = "Select a player from possible targets";
    private static final boolean FIRST_MSG_BOOL = false;

    private static final String SELECT_FIRST_TARGET_BEFORE = "Select a target for the normal firmode before";

    @Override
    public void sendPossibleTargetsAtStart() {
        sendAllVisiblePlayers(null);
    }

    @Override
    public void fire() throws WrongInputException {
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException();
        }
        else{
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 2,1);
            if(shoot.getTargetsPlayer().size() == 2){
                addDamageAndMarks(shoot.getTargetsPlayer().get(1), 0, 1);
            }
            super.fire();
        }
    }


    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> messageListExpected = new ArrayList<>();

        StringAndMessage firstTarget = new StringAndMessage(Identificator.PLAYER_MESSAGE, FIRST_MSG_STR, FIRST_MSG_BOOL);
        messageListExpected.add(firstTarget);

        return messageListExpected;
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        int targetID = target.getID();
        if(targetID == author.getID()){
            shoot.getPlayerView().printFromController("Error,you have selected yourself");
            throw new WrongInputException();
        }
        else if(target.isVisibleBy(author)){
            if(shoot.getTargetsPlayer().isEmpty()){
                shoot.addPlayerTargetFromFireMode(target);
            }
            else if(shoot.getTargetsPlayer().size() == 1 && shoot.getTargetsPlayer().get(0).getID() != targetID
                    && shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)){
                shoot.addPlayerTargetFromFireMode(target);
            }
            else{
                throw new WrongInputException();
            }

        }
        else{
            throw new WrongInputException();
        }

    }
    
    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        if(numOptional == Identificator.FIRST_OPTIONAL && shoot.getOptionalSelected().isEmpty()
                && author.canPayAmmo(AmmoBag.sumAmmoBag(shoot.getCost(), COST_FIRST_OPTIONAL))){
            if(shoot.getTargetsPlayer().isEmpty()){
                playerView.printFromController(SELECT_FIRST_TARGET_BEFORE);
                throw new WrongInputException();
            }
            else{
                shoot.addOptional(numOptional);
            }

        }
        else{
            throw new WrongInputException();
        }
    }

    @Override
    public void addNope() throws WrongInputException {
        throw new WrongInputException();
    }

}


