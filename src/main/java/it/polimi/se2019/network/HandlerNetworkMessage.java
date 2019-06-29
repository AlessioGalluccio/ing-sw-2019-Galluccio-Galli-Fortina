package it.polimi.se2019.network;

public interface HandlerNetworkMessage {

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the client who receive this message in order to handle and forward him correctly.
     * @param client The Client object who has to handle this message
     */
    void handleMessage(Client client);
}
