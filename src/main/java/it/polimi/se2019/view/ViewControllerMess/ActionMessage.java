package it.polimi.se2019.view.ViewControllerMess;

;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

/**
 * message for action chosen
 * @author Fortina
 * @author Galluccio
 */
public class ActionMessage extends ViewControllerMessage {
    private static final long serialVersionUID = 2669404197323021204L;
    private int actionID;

    /**
     * ActionMessage class's constructor
     * @param actionID the ID of the action
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public ActionMessage(int actionID, int authorID, View authorView) {
        this.actionID = actionID;
        this.messageID = Identificator.ACTION_MESSAGE;
        this.authorID= authorID;
        this.authorView= authorView;
    }

    /**
     * get the ID of the action
     * @return the int ID of the action
     */
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
