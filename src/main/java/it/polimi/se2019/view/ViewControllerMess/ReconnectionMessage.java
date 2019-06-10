package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

public class ReconnectionMessage extends ViewControllerMessage {

    private boolean isConnected;

    /**
     * FireModeMessage class's constructor
     * @param isConnected true if I'm reconnection, false if I'm disconnecting
     * @param authorID
     * @param authorView
     */
    public ReconnectionMessage(boolean isConnected, int authorID, View authorView) {
        this.isConnected= isConnected;
        this.messageID = Identificator.CONNECTION_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleReconnection(isConnected);
    }
}
