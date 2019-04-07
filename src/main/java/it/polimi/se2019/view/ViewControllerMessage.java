package it.polimi.se2019.view;

import java.util.ArrayList;

public abstract class ViewControllerMessage {
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


}
