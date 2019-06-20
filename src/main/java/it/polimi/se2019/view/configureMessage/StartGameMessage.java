package it.polimi.se2019.view.configureMessage;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.ModelViewMess.HandlerPlayerViewMessage;
import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;
import it.polimi.se2019.view.View;

public class StartGameMessage implements ModelViewMessage, HandlerPlayerViewMessage {
    private static final long serialVersionUID = 7185709958314488390L;
    private int matchID;

    public StartGameMessage(int matchID) {
        this.matchID = matchID;
    }


    @Override
    public void handleMessage(View p) {

    }

    @Override
    public int getAck() {
        return 0;
    }

    @Override
    public void handleMessage(Client client) {
        client.handleStartGame(matchID);
    }

    public int getMatchID() {
        return matchID;
    }
}
