package it.polimi.se2019.view.configureMessage;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;

public class DisconnectMessage implements HandlerNetworkMessage, Serializable {
    private static final long serialVersionUID = -9193123076232554727L;

    /**
     * Only the message itself can't know how to handle itself.
     * This method call the right method of the client who receive this message
     * in order to notify the users about the disconnection.
     * @param client The Client object which has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.handleDisconnection();
    }
}
