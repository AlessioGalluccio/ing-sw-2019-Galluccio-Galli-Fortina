package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.handler.Identificator;

public class CellMessage extends ViewControllerMessage {
    private int messageID;
    private int x;
    private int y;

    public CellMessage(int x, int y) {
        this.x = x;
        this.y = y;
        this.messageID = Identificator.CELL_MESSAGE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}


