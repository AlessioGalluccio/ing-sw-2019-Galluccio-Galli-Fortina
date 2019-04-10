package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.handler.Identificator;

public class ActionMessage extends ViewControllerMessage {
    private int messageID;
    private int actionID;

    public ActionMessage(int actionID) {
        this.actionID = actionID;
        this.messageID = Identificator.ACTION_MESSAGE;
    }

    public int getActionID() {
        return actionID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
