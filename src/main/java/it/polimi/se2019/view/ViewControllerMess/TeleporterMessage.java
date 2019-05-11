package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.handler.Identificator;

public class TeleporterMessage extends ViewControllerMessage {
    private int messageID;
    private TeleporterCard usedCard;

    public TeleporterMessage(TeleporterCard usedCard) {
        this.usedCard = usedCard;
        this.messageID = Identificator.TELEPORTER_MESSAGE;
    }

    public TeleporterCard getUsedCard() {
        return usedCard;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleTeleporter(usedCard);
    }
}
