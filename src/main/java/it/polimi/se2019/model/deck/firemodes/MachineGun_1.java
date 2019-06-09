package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;

import java.util.ArrayList;
import java.util.List;

public class MachineGun_1 extends FireMode {

    private static final String FIRST_MSG_STR = "Select a player from possible targets";
    private static final boolean FIRST_MSG_BOOL = false;

    private static final String SECOND_MSG_STR = "Select a second player from possible targets";
    private static final boolean SECOND_MSG_BOOL = true;

    private static final String OPTIONAL_SECOND_MSG = "Select a third Player";
    private static final boolean OPTIONAL_SECOND_BOOL = true;

    @Override
    public List<StringAndMessage> getMessageListExpected() {
        List<StringAndMessage> messageListExpected = new ArrayList<>();

        StringAndMessage firstTarget = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                FIRST_MSG_STR, FIRST_MSG_BOOL);
        messageListExpected.add(firstTarget);

        StringAndMessage secondTarget = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                SECOND_MSG_STR, SECOND_MSG_BOOL);
        messageListExpected.add(secondTarget);

        return messageListExpected;
    }

    @Override
    public void sendPossibleTargetsAtStart() {
        //TODO se niente target?
        sendAllVisiblePlayers(null);
    }

    @Override
    public void fire() throws WrongInputException{
        if(shoot.getTargetsPlayer().isEmpty()){
            throw new WrongInputException();
        }

        for(Player target: shoot.getTargetsPlayer()){
            addDamageAndMarks(target,1,0);
        }
        if(shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)){
            addDamageAndMarks(shoot.getTargetsPlayer().get(0), 1,0);
        }
        if(shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)){
            addDamageAndMarks(shoot.getTargetsPlayer().get(1), 1,0);
        }
        super.fire();
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
            else if(shoot.getTargetsPlayer().size() < 2 && !shoot.getTargetsPlayer().contains(target)){
                shoot.addPlayerTargetFromFireMode(target);
            }
            else if(shoot.getTargetsPlayer().size() == 2 && !shoot.getTargetsPlayer().contains(target)
                        && shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)){
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
        //TODO controlla
        if(numOptional == Identificator.FIRST_OPTIONAL
                && !shoot.getOptionalSelected().contains(Identificator.FIRST_OPTIONAL)){
            shoot.addOptional(Identificator.FIRST_OPTIONAL);
        }
        else if(numOptional == Identificator.SECOND_OPTIONAL
                && !shoot.getOptionalSelected().contains(Identificator.SECOND_OPTIONAL)
                && shoot.getTargetsPlayer().size() == 2){

            if(sendAllVisiblePlayers(shoot.getTargetsPlayer())){
                shoot.addOptional(Identificator.SECOND_OPTIONAL);
                StringAndMessage stringAndMessage = new StringAndMessage(Identificator.PLAYER_MESSAGE,
                        OPTIONAL_SECOND_MSG, OPTIONAL_SECOND_BOOL);
                controller.addMessageListExpected(stringAndMessage);
            }
            else{
                playerView.printFromController(NO_TARGET_NO_ACTION);
                throw new WrongInputException();
            }
        }
        else{
            throw new WrongInputException();
        }

    }
}
