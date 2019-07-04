package it.polimi.se2019.view.configureMessage;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.ModelViewMess.HandlerPlayerViewMessage;
import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;
import it.polimi.se2019.view.View;

/**
 * @author Galli
 */
public class StartGameMessage implements ModelViewMessage, HandlerPlayerViewMessage {
    private static final long serialVersionUID = 7185709958314488390L;
    private int matchID;

    public StartGameMessage(int matchID) {
        this.matchID = matchID;
    }

    /**
     * This message isn't handel by the view, so this method dose do nothing
     * @param view the view which has to handle this message.
     */
    @Override
    public void handleMessage(View view) {
        //DO nothing
    }

    @Override
    public int getAck() {
        return 0;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the client who receive this message
     * in order to notify the users about the start of the game.
     * @param client The Client object which has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.handleStartGame(matchID);
    }
}
