package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.NewtonCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

public class NewtonMessage extends ViewControllerMessage {

    private NewtonCard usedCard;

    /**
     * NewtonMessage class's constructor
     * @param usedCard
     * @param authorID
     * @param authorView
     */
    public NewtonMessage(NewtonCard usedCard, int authorID, View authorView) {
        this.usedCard = usedCard;
        this.messageID = Identificator.NEWTON_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

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
