package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

public class DiscardPowerupMessage extends ViewControllerMessage {
    private static final long serialVersionUID = 5729692987783697070L;
    private int powerupCardId;

    public DiscardPowerupMessage(PowerupCard powerupCard, int authorID, View authorView) {
        this.powerupCardId = powerupCard.getID();
        this.messageID = Identificator.DISCARD_POWERUP_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    public int getPowerupCardId() {
        return powerupCardId;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleDiscardPowerup(powerupCardId);
    }
}
