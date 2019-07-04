package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

/**
 * message for selecting a Teleporter powerup
 * @author Fortina
 * @author Galluccio
 */
public class TeleporterMessage extends ViewControllerMessage {

    private static final long serialVersionUID = -2889714451554044214L;
    private TeleporterCard usedCard;


    /**
     * TeleporterMessage class constructor
     * @param usedCard the TeleporterCard selected
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public TeleporterMessage(TeleporterCard usedCard, int authorID, View authorView) {

        this.usedCard = usedCard;
        this.messageID = Identificator.TELEPORTER_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    /**
     * get the TeleporterCard selected
     * @return the TeleporterCard selected
     */
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
