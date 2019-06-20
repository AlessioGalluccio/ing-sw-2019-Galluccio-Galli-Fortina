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

    public boolean isStatus() {
        return status;
    }

    public boolean isFirst() {
        return isFirst;
    }

    @Override
    public void handleMessage(Client client) {
        client.handleLoginMessage(status, isFirst, nickname);
    }
}
