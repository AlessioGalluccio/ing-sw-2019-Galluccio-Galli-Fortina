package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Action;
import it.polimi.se2019.view.PlayerView;
import it.polimi.se2019.view.ViewControllerMess.ActionMessage;
import it.polimi.se2019.view.ViewControllerMess.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private ArrayList<ViewControllerMessage> messageListReceived;
    private ArrayList<StringAndMessage> messageListExpected;
    private GameHandler gameHandler;

    public Controller() {
    }

    @Override
    public void update(Observable o, Object arg) {

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

    }

    /**
     * it empties only the messages of the optional firemode
     */
    private void flushOptionalMessages() {

    }


}
