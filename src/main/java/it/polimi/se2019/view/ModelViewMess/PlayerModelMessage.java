package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.EnemyView;

public class PlayerModelMessage implements ModelViewMessage, HandlerPlayerViewMessage, HandlerEnemyViewMessage {
    private static final long serialVersionUID = 2959843277760360653L;
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

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of View in order to handle him correctly.
     * @param p The View object which has to handle this message
     */
    @Override
    public void handleMessage(View p) {
        p.handlePlayerMessage(playerCopy);
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of EnemyView in order to handle him correctly.
     * @param e The Enemy View object which has to handle this message
     */
    @Override
    public void handleMessage(EnemyView e) {
        e.handlePlayerMessage(playerCopy);
    }

    /**
     * Only the message itself can't know how to handle itself.
     * This method call the right method of the client who receive this message in order to forward it correctly.
     * @param client The Client object which has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.forwardToClientView(this, playerCopy.getNickname());
        client.forwardToEnemyView(this, playerCopy.getNickname());
    }

    /**
     * Each message has an ack in order to handle its receiving correctly.
     * Only if this message's ack os grater the last one received should be handled.
     * This method return the ack of this message.
     * @return the ack of this message.
     */
    public int getAck() {
        return ack;
    }
}
