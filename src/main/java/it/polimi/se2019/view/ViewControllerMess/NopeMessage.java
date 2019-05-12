package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

public class NopeMessage extends ViewControllerMessage {

    private int messageID;
    private int authorID;
    private PlayerView authorView;

    /**
     * NopeMessage class's constructor
     * @param authorID
     * @param authorView
     */

    public NopeMessage(int authorID, PlayerView authorView) {

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
