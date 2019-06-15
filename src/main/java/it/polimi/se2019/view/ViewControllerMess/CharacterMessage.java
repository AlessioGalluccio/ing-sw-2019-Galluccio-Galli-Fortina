package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

public class CharacterMessage extends ViewControllerMessage {

    private int characterID;

    /**
     * PlayerMessage class constructor
     * @param characterID
     * @param authorID
     * @param authorView
     */
    public CharacterMessage(int characterID, int authorID, View authorView) {

        this.characterID = characterID;
        this.messageID = Identificator.CHARACTER_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    public int getCharacterID() {
        return characterID;
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

