package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

public class NopeMessage extends ViewControllerMessage {


    private static final long serialVersionUID = 7607227832420930780L;

    /**
     * NopeMessage class's constructor
     * @param authorID
     * @param authorView
     */
    public NopeMessage(int authorID, View authorView) {
        this.messageID = Identificator.NOPE_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleNope();
    }
}
