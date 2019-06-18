package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class LockRifle_1 extends FireMode {

    //STRING AND MESSAGE
    public static final String FIRST_MSG_STR = "Select a player from possible targets";
    public static final String SECOND_MSG_STR = "Select another player from possible targets.";

    //COST OPTIONALS
    private static final AmmoBag COST_FIRST_OPTIONAL = new AmmoBag(1,0,0);

    //ERROR MESSAGES
    private static final String SELECT_FIRST_TARGET_BEFORE = "Select a target for the normal firemode before.";





    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO se niente target?
        if(!sendAllVisiblePlayers(null)){
            playerView.printFromController(NO_TARGET_NO_ACTION);
            endFiremode();
        }
    }

    @Override
    public List<AmmoBag> costOfFiremodeNotReloading() {
        List<AmmoBag> list = new ArrayList<>();
        list.add(new AmmoBag(0,0,0)); //cost of shooting base firemode
        list.add(COST_FIRST_OPTIONAL);
        return list;
    }

    @Override
    public void fire() throws WrongInputException {
        if(!shoot.getTargetsPlayer().isEmpty()){
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 2,1, true);
            if(shoot.getTargetsPlayer().size() == 2){
                addDamageAndMarks(shoot.getTargetsPlayer().get(1), 0, 1, true);
            }
            super.fire();
        }
        else{
            throw new WrongInputException();
        }
    }


    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> messageListExpected = new ArrayList<>();

        StringAndMessage firstTarget = new StringAndMessage(Identificator.PLAYER_MESSAGE, FIRST_MSG_STR);
        messageListExpected.add(firstTarget);

        return messageListExpected;
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        Player target = gameHandler.getPlayerByID(playerID);
        int targetID = target.getID();
        if(targetID == author.getID()){
            //shoot.getPlayerView().printFromController("Error,you have selected yourself");
            throw new WrongInputException("Error,you have selected yourself");
        }
        else if(target.isVisibleBy(author)){
            if(shoot.getTargetsPlayer().isEmpty()){
                shoot.addPlayerTargetFromFireMode(target, true);
            }
            else if(shoot.getTargetsPlayer().size() == 1 && shoot.getTargetsPlayer().get(0).getID() != targetID
                    && shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)){
                shoot.addPlayerTargetFromFireMode(target, false);
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
                throw new WrongInputException(SELECT_FIRST_TARGET_BEFORE);
            }
            else{
                //TODO controlla
                if(sendAllVisiblePlayers(shoot.getTargetsPlayer())) {
                    shoot.addOptionalSelected(numOptional);
                    StringAndMessage stringAndMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                            SECOND_MSG_STR);
                    controller.addMessageListImmediateNext(stringAndMessage);
                    shoot.addCost(COST_FIRST_OPTIONAL);
                }

                else{
                    throw new WrongInputException(NO_TARGET_NO_ACTION);
                }

            }

        }
        else{
            throw new WrongInputException();
        }
    }

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException,
            NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        if(shoot.getTargetsPlayer().size() == 1) {
            super.addTargetingScope(targetingCardID, cost);
        }
        else{
            throw new WrongInputException();
        }
    }
}


