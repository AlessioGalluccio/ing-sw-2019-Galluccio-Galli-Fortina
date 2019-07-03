package it.polimi.se2019.network.messages;


public interface HandlerServerMessage {

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the object which receives this message in order to forward him correctly.
     * @param s The object which  has to switch this message
     */
    void handleMessage(SwitchServerMessage s);
}
