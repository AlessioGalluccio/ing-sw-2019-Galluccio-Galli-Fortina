package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.handler.Identificator;

public class PlayerViewMessage extends ViewControllerMessage {
    private int messageID;
    private int playerID;

    public PlayerViewMessage(int playerID) {
        this.playerID = playerID;
        this.messageID = Identificator.PLAYER_VIEW_MESSAGE;
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
