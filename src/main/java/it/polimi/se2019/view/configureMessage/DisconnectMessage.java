package it.polimi.se2019.view.configureMessage;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;

public class DisconnectMessage implements HandlerNetworkMessage, Serializable {

    private static final long serialVersionUID = -9193123076232554727L;

    @Override
    public void handleMessage(Client client) {
        client.handleDisconnection();
    }
}
