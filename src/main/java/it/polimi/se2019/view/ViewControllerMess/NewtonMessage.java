package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.NewtonCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

/**
 * message for selecting a newton powerup
 * @author Fortina
 * @author Galluccio
 */
public class NewtonMessage extends ViewControllerMessage {

    private static final long serialVersionUID = -9121530509137795994L;
    private NewtonCard usedCard;

    /**
     * NewtonMessage class constructor
     * @param usedCard the NewtonCard used
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public NewtonMessage(NewtonCard usedCard, int authorID, View authorView) {
        this.usedCard = usedCard;
        this.messageID = Identificator.NEWTON_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    /**
     * get the NewtonCard used
     * @return the NewtonCard used
     */
    public NewtonCard getUsedCard() {
        return usedCard;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleNewton(usedCard);
    }
}
