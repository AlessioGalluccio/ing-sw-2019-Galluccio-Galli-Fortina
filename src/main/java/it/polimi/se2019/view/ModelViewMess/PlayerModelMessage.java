package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.PlayerView;

public class PlayerModelMessage implements ModelViewMessage, HandlerPlayerViewMessage, HandlerEnemyViewMessage {

    private Player playerCopy;

    public PlayerModelMessage(Player playerCopy) {
        this.playerCopy = playerCopy;
    }

    public Player getPlayerCopy() {
        return playerCopy;
    }

    @Override
    public void handleMessage(View p) {
        p.handlePlayerMessage(playerCopy);
    }

    @Override
    public void handleMessage(EnemyView e) {
        e.handlePlayerMessage(playerCopy);
    }

    @Override
    public void handleMessage(Client client) {

    }
}
