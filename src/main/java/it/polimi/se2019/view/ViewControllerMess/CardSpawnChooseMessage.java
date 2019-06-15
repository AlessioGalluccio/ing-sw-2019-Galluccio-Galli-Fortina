package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

@Deprecated
public class CardSpawnChooseMessage extends ViewControllerMessage {
    private PowerupCard cardChoosen;
    private PowerupCard cardDiscarded;

    /**
     * CardSpawnChoooseMessage class's constructor
     * @param cardChoosen
     * @param cardDiscarded
     * @param authorID
     * @param authorView
     */
    public CardSpawnChooseMessage(PowerupCard cardChoosen, PowerupCard cardDiscarded, int authorID,
                                  View authorView) {
        this.cardChoosen = cardChoosen;
        this.cardDiscarded = cardDiscarded;
        this.messageID = Identificator.CARD_SPAWN_CHOOSE_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
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

    @Override
    public void handle(StateController stateController) {
        //stateController.handleCardSpawn(cardChoosen, cardDiscarded);
    }
}
