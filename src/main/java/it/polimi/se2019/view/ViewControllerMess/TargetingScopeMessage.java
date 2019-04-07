package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.TargetingScopeCard;

public class TargetingScopeMessage extends ViewControllerMessage {

    private TargetingScopeCard usedCard;

    public TargetingScopeMessage(TargetingScopeCard usedCard) {
        this.usedCard = usedCard;
    }

    public TargetingScopeCard getUsedCard() {
        return usedCard;
    }
}
