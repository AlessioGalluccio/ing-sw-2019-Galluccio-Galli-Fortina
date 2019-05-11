package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;

public class FireModeMessage extends ViewControllerMessage {
    private int messageID;
    private int firemodeID;

    public FireModeMessage(int firemodeID) {
        this.firemodeID = firemodeID;
        this.messageID = Identificator.FIRE_MODE_MESSAGE;
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
