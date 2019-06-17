package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;

public class NewTurnMessage implements ModelViewMessage, HandlerPlayerViewMessage {
    private String nickname;

    public NewTurnMessage(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void handleMessage(Client client) {
        client.forwardToClientView(this, "BROADCAST");
    }

    @Override
    public void handleMessage(View p) {
        p.handleTurnMessage(nickname);
    }

    @Override
    public int getAck() {
        return -1;
    }
}
