package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.view.PlayerView;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class ViewControllerMessage implements Serializable {
    private int authorID;
    private PlayerView authorView;
    private ArrayList<ViewControllerMessage> nextMessage;

    public ViewControllerMessage() {
    }

    public int getAuthorID() {
        return authorID;
    }

    public PlayerView getAuthorView() {
        return authorView;
    }

    /**
     * it returns the ID of the message
     * @return the integer value of the ID of the message
     */
    public abstract int getMessageID();


}
