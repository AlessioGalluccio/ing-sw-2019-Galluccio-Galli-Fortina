package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

public class FireModeMessage extends ViewControllerMessage {

    private int firemodeID;

    /**
     * FireModeMessage class's constructor
     * @param firemodeID
     * @param authorID
     * @param authorView
     */
    public FireModeMessage(int firemodeID, int authorID, View authorView) {
        this.firemodeID = firemodeID;
        this.messageID = Identificator.FIRE_MODE_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    public int getFiremodeID() {
        return firemodeID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleFiremode(firemodeID);
    }
}
