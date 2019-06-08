package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;

public class StartGameMessage implements ModelViewMessage, HandlerPlayerViewMessage {



    @Override
    public void handleMessage(View p) {
        p.handleStartGameMessage(this);
    }

    @Override
    public void handleMessage(Client client) {

    }

}
