package it.polimi.se2019.view;

import it.polimi.se2019.model.deck.NewtonCard;

public class NewtonMessage extends ViewControllerMessage {

    private NewtonCard usedCard;

    public NewtonMessage(NewtonCard usedCard) {
        this.usedCard = usedCard;
    }

    public NewtonCard getUsedCard() {
        return usedCard;
    }
}
