package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

public class OptionalMessage extends ViewControllerMessage{
    private int optionalID;

    /**
     * FireModeMessage class's constructor
     * @param optionalID
     * @param authorID
     * @param authorView
     */
    public OptionalMessage(int optionalID, int authorID, View authorView) {
        this.optionalID = optionalID;
        this.messageID = Identificator.OPTIONAL_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    public int getOptionalID() {
        return optionalID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleOptional(optionalID);
    }
}
