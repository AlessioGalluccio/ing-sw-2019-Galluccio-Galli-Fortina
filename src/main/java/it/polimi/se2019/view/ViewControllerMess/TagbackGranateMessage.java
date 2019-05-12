package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.TagbackGranedCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

public class TagbackGranateMessage extends ViewControllerMessage {

    private TagbackGranedCard usedCard;

    /**
     * TagbackGranateMessage class's constructor
     * @param usedCard
     * @param authorID
     * @param authorView
     */
    public TagbackGranateMessage(TagbackGranedCard usedCard, int authorID, PlayerView authorView) {

        this.usedCard = usedCard;
        this.messageID = Identificator.TAGBACK_GRANADE_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    public TagbackGranedCard getUsedCard() {
        return usedCard;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleTagback(usedCard);
    }
}
