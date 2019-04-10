package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.NewtonCard;
import it.polimi.se2019.model.handler.Identificator;

public class NewtonMessage extends ViewControllerMessage {
    private int messageID;
    private NewtonCard usedCard;

    public NewtonMessage(NewtonCard usedCard) {
        this.usedCard = usedCard;
        this.messageID = Identificator.NEWTON_MESSAGE;
    }

    public NewtonCard getUsedCard() {
        return usedCard;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
