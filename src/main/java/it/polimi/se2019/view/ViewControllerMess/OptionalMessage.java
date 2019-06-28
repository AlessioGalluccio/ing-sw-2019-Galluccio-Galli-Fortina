package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

public class OptionalMessage extends ViewControllerMessage{
    private static final long serialVersionUID = -1651278236277548533L;
    private int optionalID;

    /**
     * OptionalMessage class's constructor
     * @param optionalID the ID of the optional firemode selected
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public OptionalMessage(int optionalID, int authorID, View authorView) {
        this.optionalID = optionalID;
        this.messageID = Identificator.OPTIONAL_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
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
