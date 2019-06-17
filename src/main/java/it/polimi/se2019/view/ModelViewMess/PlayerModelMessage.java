package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.EnemyView;

public class PlayerModelMessage implements ModelViewMessage, HandlerPlayerViewMessage, HandlerEnemyViewMessage {
    private static int ID = 1;
    private int ack;
    private Player playerCopy;

    public PlayerModelMessage(Player playerCopy) {
        this.playerCopy = playerCopy;
        ID++;
        ack = ID;
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
        client.forwardToClientView(this, playerCopy.getNickname());
        client.forwardToEnemyView(this, playerCopy.getNickname());
    }

    public int getAck() {
        return ack;
    }
}
