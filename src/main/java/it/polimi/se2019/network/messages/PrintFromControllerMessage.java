package it.polimi.se2019.network.messages;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;

public class PrintFromControllerMessage implements HandlerNetworkMessage, Serializable {
    private String message;

    public PrintFromControllerMessage(String string) {
        this.message = string;
    }

    @Override
    public void handleMessage(Client client) {
        client.printFromController(message);
    }
}