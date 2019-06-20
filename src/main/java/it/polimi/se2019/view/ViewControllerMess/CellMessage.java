package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

public class CellMessage extends ViewControllerMessage {

    private static final long serialVersionUID = 6233435177809673201L;
    private int x;
    private int y;

    /**
     * CellMessage class's constructor
     * @param x
     * @param y
     * @param authorID
     * @param authorView
     */
    public CellMessage(int x, int y, int authorID, View authorView) {
        this.x = x;
        this.y = y;
        this.messageID = Identificator.CELL_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
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

    @Override
    public void handle(StateController stateController) {
        stateController.handleCell(x,y);
    }
}


