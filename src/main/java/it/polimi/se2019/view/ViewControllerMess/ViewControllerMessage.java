package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.network.messages.HandlerServerMessage;
import it.polimi.se2019.network.messages.SwitchServerMessage;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * message from view to controller
 * @author Fortina
 * @author Galluccio
 */
public abstract class ViewControllerMessage implements HandlerServerMessage, Serializable {
    protected int authorID; //Protected: can be accessed only in this package, so only by class message. It's OK!
    protected View authorView;
    protected int messageID;
    private ArrayList<ViewControllerMessage> nextMessage;


    public int getAuthorID() {
        return authorID;
    }

    public View getAuthorView() {
        return authorView;
    }

    /**
     * it returns the ID of the message
     * @return the integer value of the ID of the message
     */
    public abstract int getMessageID();

    /**
     * handles this message, calling the correct method in a StateController
     * @param stateController the StateController of the controller of the player
     */
    public abstract void handle(StateController stateController);


    /**
     * set the PlayerView parameter of the message
     * @param view
     */
    public void setView(PlayerView view) {
        authorView = view;
    }

    /**
     * handles the message
     * @param s the SwitchServerMessage
     */
    public void handleMessage(SwitchServerMessage s) {
        s.forwardViewMessage(this);
    }
}
