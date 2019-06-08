package it.polimi.se2019.view.configureMessage;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;

public class StatusLoginMessage implements HandlerNetworkMessage, Serializable {
    private boolean status;
    private boolean isFirst;

    public StatusLoginMessage(boolean status, boolean isFirst) {
        this.status = status;
        this.isFirst = isFirst;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isFirst() {
        return isFirst;
    }

    @Override
    public void handleMessage(Client client) {
        client.handleLoginMessage(status, isFirst);
    }
}
