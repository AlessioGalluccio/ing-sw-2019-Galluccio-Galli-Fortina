package it.polimi.se2019.view.configureMessage;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;

public class EnemyMessage implements HandlerNetworkMessage, Serializable {
    private String nickname;

    public EnemyMessage(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void handleMessage(Client client) {
        client.handleEnemyMessage(nickname);
    }
}
