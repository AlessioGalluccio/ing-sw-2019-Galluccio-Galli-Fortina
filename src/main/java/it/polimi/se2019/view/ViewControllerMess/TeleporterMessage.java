package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.TeleporterCard;

public class TeleporterMessage extends ViewControllerMessage {

    private TeleporterCard usedCard;

    public TeleporterMessage(TeleporterCard usedCard) {
        this.usedCard = usedCard;
    }

    public TeleporterCard getUsedCard() {
        return usedCard;
    }
}
