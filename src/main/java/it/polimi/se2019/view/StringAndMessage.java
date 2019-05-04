package it.polimi.se2019.view;

import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

public class StringAndMessage {
    private int messageId;
    private String string;
    private boolean optional;

    public StringAndMessage(int messageId, String string, boolean optional) {
        this.messageId = messageId;
        this.string = string;
        this.optional = optional;
    }

    public String getString() {
        return string;
    }

    public int getMessageID() {
        return messageId;
    }

    public boolean isOptional() {
        return optional;
    }
}
