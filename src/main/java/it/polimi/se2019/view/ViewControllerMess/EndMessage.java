package it.polimi.se2019.view.ViewControllerMess;

public class EndMessage extends ViewControllerMessage {

    private int messageID;


    public EndMessage(int messageID) {

        this.messageID = messageID;
    }

    @Override
    public int getMessageID() {
        return messageID ;
    }
}
