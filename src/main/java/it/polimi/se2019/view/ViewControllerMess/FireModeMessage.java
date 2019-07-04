package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

/**
 * message for selecting a non optional firemode (base or alternative)
 * @author Fortina
 * @author Galluccio
 */
public class FireModeMessage extends ViewControllerMessage {

    private static final long serialVersionUID = -4599843747261210499L;
    private int firemodeID;

    /**
     * FireModeMessage class constructor
     * @param firemodeID the ID of the firemode selected
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public FireModeMessage(int firemodeID, int authorID, View authorView) {
        this.firemodeID = firemodeID;
        this.messageID = Identificator.FIRE_MODE_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    /**
     * get the ID of the firemode selected
     * @return the int ID of the firemode selected
     */
    public int getFiremodeID() {
        return firemodeID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleFiremode(firemodeID);
    }
}
