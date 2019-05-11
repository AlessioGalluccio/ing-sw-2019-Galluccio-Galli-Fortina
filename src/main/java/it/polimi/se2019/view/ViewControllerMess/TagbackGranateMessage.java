package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.TagbackGranedCard;
import it.polimi.se2019.model.handler.Identificator;

public class TagbackGranateMessage extends ViewControllerMessage {
    private int messageID;
    private TagbackGranedCard usedCard;

    public TagbackGranateMessage(TagbackGranedCard usedCard) {
        this.usedCard = usedCard;
        this.messageID = Identificator.TAGBACK_GRANADE_MESSAGE;
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
