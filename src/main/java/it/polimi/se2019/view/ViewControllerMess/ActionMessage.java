package it.polimi.se2019.view.ViewControllerMess;

;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

public class ActionMessage extends ViewControllerMessage {
    private int actionID;

    /**
     * ActionMessage class's constructor
     * @param actionID
     * @param authorID
     * @param authorView
     */
    public ActionMessage(int actionID, int authorID, View authorView) {
        this.actionID = actionID;
        this.messageID = Identificator.ACTION_MESSAGE;
        this.authorID= authorID;
        this.authorView= authorView;
    }

    public int getActionID() {
        return actionID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleAction(actionID);
    }
}
