package it.polimi.se2019.network;

public interface HandlerNetworkMessage {

    /**
     * Only the message itself can't know how to handle itself.
     * This method call the right method of the client which receive this message in order to handle and forward it correctly.
     * @param client The Client object which has to handle this message
     */
    void handleMessage(Client client);
}
