package it.polimi.se2019.network;

import it.polimi.se2019.model.handler.GameHandler;

public class DisconnectedException extends Exception {
    GameHandler gameHandler;
    String nickname;

    public DisconnectedException() {
        super();
    }

    public DisconnectedException(GameHandler gm, String nickname) {
        this.gameHandler = gm;
        this.nickname = nickname;
    }
}
