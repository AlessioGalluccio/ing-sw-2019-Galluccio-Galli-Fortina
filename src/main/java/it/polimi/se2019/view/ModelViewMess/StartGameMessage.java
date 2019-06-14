package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;

public class StartGameMessage implements ModelViewMessage, HandlerPlayerViewMessage {
    private int matchID;

    public StartGameMessage(int matchID) {
        this.matchID = matchID;
    }

    @Override
    public void handleMessage(View p) {
        p.handleStartGameMessage(matchID);
    }

    @Override
    public int getAck() {
        return 0;
    }

    @Override
    public void handleMessage(Client client) {
        client.forwardToClientView(this);
    }

    public int getMatchID() {
        return matchID;
    }
}
