package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

public class TeleporterMessage extends ViewControllerMessage {

    private int messageID;
    private TeleporterCard usedCard;
    private int authorID;
    private PlayerView authorView;


    /**
     * TeleporterMessage class's constructor
     * @param usedCard
     * @param authorID
     * @param authorView
     */

    public TeleporterMessage(TeleporterCard usedCard, int authorID, PlayerView authorView) {

        this.usedCard = usedCard;
        this.messageID = Identificator.TELEPORTER_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
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
