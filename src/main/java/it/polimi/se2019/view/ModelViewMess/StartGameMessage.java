package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.view.remoteView.PlayerView;

public class StartGameMessage implements ModelViewMessage, HandlerPlayerViewMessage {

    @Override
    public void handleMessage(PlayerView p) {
        p.handleStartGameMessage(this);
    }
}
