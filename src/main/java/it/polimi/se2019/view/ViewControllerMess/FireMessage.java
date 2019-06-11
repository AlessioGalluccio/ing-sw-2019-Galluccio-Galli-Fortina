package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

public class FireMessage extends ViewControllerMessage {

    /**
     * FireMessage class's constructor
     * @param authorID
     * @param authorView
     */
    public FireMessage(int authorID, View authorView) {

        this.messageID = Identificator.FIRE_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    @Override
    public int getMessageID() {
        return messageID ;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleFire();
    }
}
