package it.polimi.se2019.view.ViewControllerMess;

;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

public class ActionMessage extends ViewControllerMessage {
    private int messageID;
    private int actionID;
    private int authorID;
    private PlayerView authorView;

    /**
     * ActionMessage class's constructor
     * @param actionID
     * @param authorID
     * @param authorView
     */

    public ActionMessage(int actionID,int authorID,PlayerView authorView) {
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
