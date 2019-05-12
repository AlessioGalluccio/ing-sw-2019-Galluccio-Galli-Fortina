package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.NewtonCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

public class NewtonMessage extends ViewControllerMessage {

    private int messageID;
    private NewtonCard usedCard;
    private int authorID;
    private PlayerView authorView;

    /**
     * NewtonMessage class's constructor
     * @param usedCard
     * @param authorID
     * @param authorView
     */

    public NewtonMessage(NewtonCard usedCard, int authorID, PlayerView authorView) {
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
