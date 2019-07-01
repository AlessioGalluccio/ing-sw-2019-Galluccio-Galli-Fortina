package it.polimi.se2019.view.configureMessage;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;

public class StatusLoginMessage implements HandlerNetworkMessage, Serializable {
    private static final long serialVersionUID = 5877877795652127661L;
    private boolean status;
    private boolean isFirst;
    private String nickname;

    public StatusLoginMessage(boolean status, boolean isFirst, String nickname) {
        this.status = status;
        this.isFirst = isFirst;
        this.nickname = nickname;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the client who receive this message
     * in order to notify the user about the status of the login.
     * @param client The Client object which has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.handleLoginMessage(status, isFirst, nickname);
    }
}
