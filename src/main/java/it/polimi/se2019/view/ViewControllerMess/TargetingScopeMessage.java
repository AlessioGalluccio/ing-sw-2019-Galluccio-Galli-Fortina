package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.handler.Identificator;

public class TargetingScopeMessage extends ViewControllerMessage {
    private int messageID;
    private TargetingScopeCard usedCard;

    public TargetingScopeMessage(TargetingScopeCard usedCard) {
        this.usedCard = usedCard;
        this.messageID = Identificator.TARGETING_SCOPE_MESSAGE;
    }

    public TargetingScopeCard getUsedCard() {
        return usedCard;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
