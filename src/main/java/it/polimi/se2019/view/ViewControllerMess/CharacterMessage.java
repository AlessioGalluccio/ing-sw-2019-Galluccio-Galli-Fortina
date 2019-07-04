package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

/**
 * message for Character selected
 * @author Fortina
 * @author Galluccio
 */
public class CharacterMessage extends ViewControllerMessage {

    private static final long serialVersionUID = -4982503757769267408L;
    private int characterID;

    /**
     * CharacterMessage class constructor
     * @param characterID the ID of the character
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public CharacterMessage(int characterID, int authorID, View authorView) {

        this.characterID = characterID;
        this.messageID = Identificator.CHARACTER_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleCharacter(characterID);
    }
}

