package it.polimi.se2019.view.configureMessage;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;

/**
 * @author Galli
 */
public class EnemyMessage implements HandlerNetworkMessage, Serializable {
    private static final long serialVersionUID = -55782681386881588L;
    private String nickname;

    public EnemyMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the client who receive this message
     * in order to create an enemy view for this player.
     * @param client The Client object which has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.handleEnemyMessage(nickname);
    }
}
