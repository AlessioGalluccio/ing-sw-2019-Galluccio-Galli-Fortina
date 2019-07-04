package it.polimi.se2019.view;

/**
 * it contains the message expected in an interaction and a string request to print to the terminal of the user
 * @author Galluccio
 */
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
