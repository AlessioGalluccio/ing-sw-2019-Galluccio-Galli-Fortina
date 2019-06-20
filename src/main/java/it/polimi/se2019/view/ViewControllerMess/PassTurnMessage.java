package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

public class PassTurnMessage extends ViewControllerMessage {
    private static final long serialVersionUID = 6933077226306380352L;

    //TO PASS THE TURN

    /**
     * PassTurnMessage class's constructor
     * @param authorID
     * @param authorView
     */
    public PassTurnMessage(int authorID, View authorView) {

        this.messageID = Identificator.PASS_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    @Override
    public int getMessageID() {
        return messageID ;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handlePassTurn();
    }
}
