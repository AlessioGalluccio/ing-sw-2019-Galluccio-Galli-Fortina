package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

public class UndoMessage extends  ViewControllerMessage {

    //TODO classe forse da eliminare


    /**
     * UndoMessage class's constructor
     * @param authorID
     * @param authorView
     */

    public UndoMessage(int authorID, PlayerView authorView) {

        this.messageID = Identificator.UNDO_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
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
