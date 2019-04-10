package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.handler.Identificator;

public class CardSpawnChooseMessage extends ViewControllerMessage {
    private int messageID;
    private PowerupCard cardChoosen;
    private PowerupCard cardDiscarded;

    public CardSpawnChooseMessage(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        this.cardChoosen = cardChoosen;
        this.cardDiscarded = cardDiscarded;
        this.messageID = Identificator.CARD_SPAWN_CHOOSE_MESSAGE;
    }

    public PowerupCard getCardChoosen() {
        return cardChoosen;
    }

    public PowerupCard getCardDiscarded() {
        return cardDiscarded;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
