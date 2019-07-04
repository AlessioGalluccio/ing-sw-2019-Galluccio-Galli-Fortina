package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.TagbackGrenadeCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

/**
 * @author Fortina
 */
public class TagbackGrenadeMessage extends ViewControllerMessage {

    private static final long serialVersionUID = -6223826044088291462L;
    private TagbackGrenadeCard usedCard;

    /**
     * TagbackGrenadeMessage class constructor
     * @param usedCard TagbackGrenadeCard selected
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public TagbackGrenadeMessage(TagbackGrenadeCard usedCard, int authorID, View authorView) {

        this.usedCard = usedCard;
        this.messageID = Identificator.TAGBACK_GRANADE_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    /**
     * get the selected TagbackGrenadeCard
     * @return the TagbackGrenadeCard selected
     */
    public TagbackGrenadeCard getUsedCard() {
        return usedCard;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleTagback(usedCard);
    }
}
