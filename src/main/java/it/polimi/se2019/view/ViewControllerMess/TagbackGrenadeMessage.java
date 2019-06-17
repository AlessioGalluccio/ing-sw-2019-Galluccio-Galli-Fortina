package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.TagbackGrenadeCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

public class TagbackGrenadeMessage extends ViewControllerMessage {

    private TagbackGrenadeCard usedCard;

    /**
     * TagbackGrenadeMessage class's constructor
     * @param usedCard
     * @param authorID
     * @param authorView
     */
    public TagbackGrenadeMessage(TagbackGrenadeCard usedCard, int authorID, View authorView) {

        this.usedCard = usedCard;
        this.messageID = Identificator.TAGBACK_GRANADE_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    public TagbackGrenadeCard getUsedCard() {
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