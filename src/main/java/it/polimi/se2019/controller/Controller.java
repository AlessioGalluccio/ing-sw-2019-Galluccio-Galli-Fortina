package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.Action;
import it.polimi.se2019.view.PlayerView;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private ArrayList<ViewControllerMessage> messageListReceived;
    private ArrayList<StringAndMessage> messageListExpected;
    private int indexExpected = 0;
    private GameHandler gameHandler;

    public Controller(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.messageListReceived = new ArrayList<ViewControllerMessage>();
        this.messageListExpected = new ArrayList<StringAndMessage>();
    }

    @Override
    public void update(Observable o, Object arg) {

    }


    public void update(Observable o, PlayerViewMessage arg) {
        //TODO

        //new Message
        if(messageListExpected.isEmpty() && arg.getMessageID() == Identificator.ACTION_MESSAGE) {
            messageListReceived.add(arg);
            messageListExpected = (gameHandler.getActionByID(arg.getMessageID()).getStringAndMessageExpected());
        }

        //middle cases
        else if(arg.getMessageID() == messageListExpected.get(indexExpected).getMessage().getMessageID()) {
            if(arg.getMessageID() == Identificator.FIRE_MODE_MESSAGE) {
                //TODO possibleTargets da spedire alla View
            }
            if(arg.getMessageID() == Identificator.NEWTON_MESSAGE) {
                //TODO possibleTargets da spedire alla View
            }
            if(arg.getMessageID() == Identificator.TAGBACK_GRANADE_MESSAGE) {
                //TODO possibleTargets da spedire alla View
            }

            messageListReceived.add(arg);
            indexExpected++;
        }

        //NOPE message
        else if(arg.getMessageID() == Identificator.NOPE_MESSAGE) {
            if (messageListExpected.get(indexExpected).isOptional()) {
                indexExpected++;
            }
            else {
                //TODO messaggio da stampare alla view
                arg.getAuthorView().printFromController("Warning, this action is required");
            }
        }

        //UNDO message
        //else if(arg.getMessageID() == Identificator.UNDO_MESSAGE) {
            //TODO da vedere se ha senso di esistere!
            //indexExpected--;
            //messageListReceived.remove(indexExpected);
        //}

        //End of series of messages
        else if(messageListExpected.size() == indexExpected) {
            //TODO inserire modifyModel
            //modifyModel();

            flushMessages();
        }

        //Message is wrong, an error massage is printed in the view of the player
        else {
            arg.getAuthorView().printFromController(messageListExpected.get(indexExpected).getString());
        }

    }

    /**
     * command the model to send to the view the possible targets of this firemode
     * @param fireModeMess the message of this firemode
     */
    private void askPossibleTargets(FireModeMessage fireModeMess) {
        //TODO
    }

    /**
     * command the model to send to the view the possible targets of this tagbackGranate card
     * @param tagbackGranateMess the message of this tagbackGranade card
     */
    private void askPossibleTargets(TagbackGranateMessage tagbackGranateMess) {
        //TODO
    }

    /**
     * command the model to send to the view the possible targets of this Newton card
     * @param newtonMess the message of this Newton card
     */
    private void askPossibleTargets(NewtonMessage newtonMess) {
        //TODO
    }

    /**
     * It verififies the list of messages and, if it is correct, it modifies the model accordingly
     * @param actionMessage the first message in the list. It defines what type of action
     */
    private void modifyModel(ActionMessage actionMessage) {
        //TODO verify()
        gameHandler.getActionByID(actionMessage.getActionID()).executeAction(gameHandler.getPlayerByID(actionMessage.getAuthorID()), messageListReceived);

    }

    private void modifyModel(PowerupCard powerupCard) {

    }

    /**
     * it empties the messages after Model is modified
     */
    private void flushMessages() {
        messageListReceived = new ArrayList<ViewControllerMessage>();
        this.messageListExpected = new ArrayList<StringAndMessage>();
        indexExpected = 0;
    }

    /**
     * it empties only the messages of the optional firemode
     */
    private void flushOptionalMessages() {
        //TODO
    }


}
