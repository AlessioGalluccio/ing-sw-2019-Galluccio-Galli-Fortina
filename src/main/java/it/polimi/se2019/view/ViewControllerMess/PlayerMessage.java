package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

public class PlayerMessage extends ViewControllerMessage {

    private static final long serialVersionUID = 5394848968896150380L;
    private int playerID;

    /**
     * PlayerMessage class constructor
     * @param playerID the ID of the player selected
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public PlayerMessage(int playerID, int authorID, View authorView) {

        this.playerID = playerID;
        this.messageID = Identificator.PLAYER_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    /**
     * get the ID of the player selected
     * @return the int ID of the player selected
     */
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handlePlayer(playerID);
    }
}
