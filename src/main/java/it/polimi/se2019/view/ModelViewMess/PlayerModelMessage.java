package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;

public class PlayerModelMessage implements ModelViewMessage {

    private Player playerCopy;

    public PlayerModelMessage(Player playerCopy) {
        this.playerCopy = playerCopy;
    }

    public Player getPlayerCopy() {
        return playerCopy;
    }
}
