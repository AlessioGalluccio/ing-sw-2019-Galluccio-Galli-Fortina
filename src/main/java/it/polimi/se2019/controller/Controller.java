package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private ArrayList<ViewControllerMessage> messageListReceived;
    private ArrayList<StringAndMessage> messageListExpected;
    private int indexExpected = 0;
    private final GameHandler gameHandler;
    private StateController state;
    private int numOfActionTaken;
    public final int MAX_NUM_OF_ACTION = 2;

    public Controller(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.messageListReceived = new ArrayList<ViewControllerMessage>();
        this.messageListExpected = new ArrayList<StringAndMessage>();
        this.numOfActionTaken = 0;
    }

    //getter and Setters

    public ArrayList<ViewControllerMessage> getMessageListReceived() {
        return messageListReceived;
    }

    public ArrayList<StringAndMessage> getMessageListExpected() {
        return messageListExpected;
    }

    public int getIndexExpected() {
        return indexExpected;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public StateController getState() {
        return state;
    }

    public int getNumOfActionTaken() {
        return numOfActionTaken;
    }

    public void setMessageListReceived(ArrayList<ViewControllerMessage> messageListReceived) {
        this.messageListReceived = messageListReceived;
    }

    public void setMessageListExpected(ArrayList<StringAndMessage> messageListExpected) {
        this.messageListExpected = messageListExpected;
    }

    public void setIndexExpected(int indexExpected) {
        this.indexExpected = indexExpected;
    }

    public void setState(StateController state) {
        this.state = state;
    }

    public void setNumOfActionTaken(int numOfActionTaken) {
        this.numOfActionTaken = numOfActionTaken;
    }

    //methods

    @Override
    public void update(Observable o, Object arg) {
        state.handle(arg);

    }

    /*

    public void handleMessage(Object arg) {
        //TODO lancia eccezione, è un tipo di messaggio non aspettato
    }

    public void handleMessage(PlayerViewMessage arg) {
        //TODO

        //new Message
        if(messageListExpected.isEmpty() && arg.getMessageID() == Identificator.ACTION_MESSAGE) {
            messageListReceived.add(arg);
            messageListExpected = (gameHandler.getActionByID(arg.getMessageID()).getStringAndMessageExpected());
        }


        //middle cases
        else if(arg.getMessageID() == messageListExpected.get(indexExpected).getMessage().getMessageID()) {
            if(arg.getMessageID() == Identificator.FIRE_MODE_MESSAGE) {
                sendTargetsToView(arg);
            }
            if(arg.getMessageID() == Identificator.NEWTON_MESSAGE) {
                sendTargetsToView(arg);
            }
            if(arg.getMessageID() == Identificator.TAGBACK_GRANADE_MESSAGE) {
                sendTargetsToView(arg);
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

    /*

    /**
     * command the model to send to the view the possible targets of this firemode
     * @param arg the message of this firemode
     */
    public void sendTargetsToView(FireModeMessage arg) {
        Player playerTemp = gameHandler.getPlayerByID(arg.getAuthorID());
        AmmoBag ammoTemp = playerTemp.getAmmo();
        FireMode fireModeTemp = gameHandler.getFireModeByID(arg.getAuthorID());
        //TODO metodo che compara fireModeTemp.getCost() con ammoTemp. Se non bastano, chiedono a giocatore di scartare un potenziamento se ce l'ha

        //TODO fireModeTemp.sendPossibleTargetsToView(arg.getAuthorView);
    }

    /**
     * command the model to send to the view the possible targets of this tagbackGranate card
     * @param arg the message of this tagbackGranade card
     */
    public void sendTargetsToView(TagbackGranateMessage arg) {
        Player author = gameHandler.getPlayerByID(arg.getAuthorID());
        TagbackGranedCard card = arg.getUsedCard();
        card.sendPossibleTarget(author, arg.getAuthorView());
        //TODO
    }

    /**
     * command the model to send to the view the possible targets of this Newton card
     * @param arg the message of this Newton card
     */
    public void sendTargetsToView(NewtonMessage arg) {
        Player author = gameHandler.getPlayerByID(arg.getAuthorID());
        NewtonCard card = arg.getUsedCard();
        card.sendPossibleTarget(author, arg.getAuthorView());
        //TODO
    }

    /**
     * overloading has failed, send message error
     * @param arg the wrong type of message
     */
    public void sendTargetsToView(PlayerViewMessage arg) {
        //TODO eccezione messaggio inaspettato
    }


    /**
     * It verififies the list of messages and, if it is correct, it modifies the model accordingly
     * @param actionMessage the first message in the list. It defines what type of action
     */
    public void modifyModel(ActionMessage actionMessage) {
        //TODO verify()
        gameHandler.getActionByID(actionMessage.getActionID()).executeAction(gameHandler.getPlayerByID(actionMessage.getAuthorID()), messageListReceived);

    }

    public void modifyModel(PowerupCard powerupCard) {
        //TODO
    }

    /**
     * general case, should not be used
     * @param message
     */
    public void modifyModel(ViewControllerMessage message) {
        //TODO scrivi eccezione!
    }

    /**
     * it empties the messages after Model is modified
     */
    public void flushMessages() {
        messageListReceived = new ArrayList<ViewControllerMessage>();
        this.messageListExpected = new ArrayList<StringAndMessage>();
        indexExpected = 0;
    }

    /**
     * it empties only the messages of the optional firemode
     */
    public void flushOptionalMessages() {
        //TODO
    }


}
