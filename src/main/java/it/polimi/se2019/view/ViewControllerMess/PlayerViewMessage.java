package it.polimi.se2019.view.ViewControllerMess;

public class PlayerViewMessage extends ViewControllerMessage {

    private int playerID;

    public PlayerViewMessage(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }
}