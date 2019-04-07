package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.PowerupCard;

public class CardSpawnChooseMessage extends ViewControllerMessage {

    private PowerupCard cardChoosen;
    private PowerupCard cardDiscarded;

    public CardSpawnChooseMessage(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        this.cardChoosen = cardChoosen;
        this.cardDiscarded = cardDiscarded;
    }

    public PowerupCard getCardChoosen() {
        return cardChoosen;
    }

    public PowerupCard getCardDiscarded() {
        return cardDiscarded;
    }
}
