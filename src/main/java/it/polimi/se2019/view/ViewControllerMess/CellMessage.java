package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

/**
 * message for target cell
 * @author Fortina
 * @author Galluccio
 */
public class CellMessage extends ViewControllerMessage {

    private static final long serialVersionUID = 6233435177809673201L;
    private int x;
    private int y;

    /**
     * CellMessage class's constructor
     * @param x the coordinate X of the cell
     * @param y the coordinate Y of the cell
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public CellMessage(int x, int y, int authorID, View authorView) {
        this.x = x;
        this.y = y;
        this.messageID = Identificator.CELL_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    /**
     * get the coordinate X of the cell
     * @return the int value of the X coordinate of the cell
     */
    public int getX() {
        return x;
    }

    /**
     * get the coordinate Y of the cell
     * @return the int value of the Y coordinate of the cell
     */
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


