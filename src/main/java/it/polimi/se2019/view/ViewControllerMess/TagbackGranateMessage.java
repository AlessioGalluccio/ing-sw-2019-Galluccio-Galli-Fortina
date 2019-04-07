package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.TagbackGranedCard;

public class TagbackGranateMessage extends ViewControllerMessage {

    private TagbackGranedCard usedCard;

    public TagbackGranateMessage(TagbackGranedCard usedCard) {
        this.usedCard = usedCard;
    }

    public TagbackGranedCard getUsedCard() {
        return usedCard;
    }
}
