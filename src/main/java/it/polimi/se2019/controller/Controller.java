package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.player.Action;
import it.polimi.se2019.view.ViewControllerMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private ArrayList<ViewControllerMessage> messageListReceived;
    private ArrayList<ViewControllerMessage> messageListExpected;

    public Controller() {
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public ArrayList<ViewControllerMessage> getMessageListReceived() {
        return messageListReceived;
    }

    public ArrayList<ViewControllerMessage> getMessageListExpected() {
        return messageListExpected;
    }

    public void setMessageListReceived(ArrayList<ViewControllerMessage> messageListReceived) {
        this.messageListReceived = messageListReceived;
    }

    public void setMessageListExpected(ArrayList<ViewControllerMessage> messageListExpected) {
        this.messageListExpected = messageListExpected;
    }

    private void modifyModel(Action action) {

    }

    private void modifyModel(PowerupCard powerupCard) {

    }


}
