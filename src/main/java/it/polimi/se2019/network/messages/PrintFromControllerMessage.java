package it.polimi.se2019.network.messages;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;

/**
 * @author Galli
 */
public class PrintFromControllerMessage implements HandlerNetworkMessage, Serializable {
    private static final long serialVersionUID = -1838299026500487559L;
    private String message;

    public PrintFromControllerMessage(String string) {
        this.message = string;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the client who receive this message in order to handle and forward him correctly.
     * @param client The Client object who has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.printFromController(message);
    }
}
