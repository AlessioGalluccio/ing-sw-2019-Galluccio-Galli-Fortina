package it.polimi.se2019.view;

import it.polimi.se2019.model.player.Move;

public class MoveMessage extends ViewControllerMessage {
    private Move move;

    public MoveMessage(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }
}
