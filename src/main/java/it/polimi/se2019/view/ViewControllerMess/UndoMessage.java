package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;

public class UndoMessage extends  ViewControllerMessage {

    //TODO classe forse da eliminare

    private int messageID;

    public UndoMessage() {
        this.messageID = Identificator.UNDO_MESSAGE;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        //TODO
    }
}
