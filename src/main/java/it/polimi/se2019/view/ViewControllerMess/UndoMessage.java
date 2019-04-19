package it.polimi.se2019.view.ViewControllerMess;

public class UndoMessage extends  ViewControllerMessage {

    private int messageID;

    public UndoMessage(int messageID) {
        this.messageID = messageID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
