package it.polimi.se2019.view.ViewControllerMess;

public class ActionMessage extends ViewControllerMessage {
    private int actionID;

    public ActionMessage(int actionID) {
        this.actionID = actionID;
    }

    public int getActionID() {
        return actionID;
    }
}
