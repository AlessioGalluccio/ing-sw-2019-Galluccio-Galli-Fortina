package it.polimi.se2019.view;

import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

public class StringAndMessage {
    private int messageId;
    private String string;

    public StringAndMessage(int messageId, String string) {
        this.messageId = messageId;
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public int getMessageID() {
        return messageId;
    }
}
