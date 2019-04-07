package it.polimi.se2019.view;

import it.polimi.se2019.model.player.Player;

public class PlayerModelMessage implements ModelViewMessage {

    private Player playerCopy;

    public PlayerModelMessage(Player playerCopy) {
        this.playerCopy = playerCopy;
    }

    public Player getPlayerCopy() {
        return playerCopy;
    }
}
