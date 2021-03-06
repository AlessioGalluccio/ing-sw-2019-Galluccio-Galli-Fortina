package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

/**
 * message for skipping the action
 * @author Fortina
 * @author Galluccio
 */
public class NopeMessage extends ViewControllerMessage {


    private static final long serialVersionUID = 7607227832420930780L;

    /**
     * NopeMessage class constructor
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
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
