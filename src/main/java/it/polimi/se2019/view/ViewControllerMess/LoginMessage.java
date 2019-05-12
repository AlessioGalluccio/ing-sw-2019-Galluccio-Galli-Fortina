package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.view.remoteView.PlayerView;

public class LoginMessage extends ViewControllerMessage{

    private String playerNickname;
    private Character chosenCharacter;

    /**
     * LoginMessage class's constructor
     * @param playerNickname
     * @param choosenCharacter
     * @param authorID
     * @param authorView
     */
    public LoginMessage(String playerNickname, Character choosenCharacter, int authorID, PlayerView authorView) {

        this.messageID = Identificator.LOGIN_MESSAGE;
        this.playerNickname = playerNickname;
        this.chosenCharacter = choosenCharacter;
        this.authorID = authorID;
        this.authorView = authorView;

    }

    public Character getChoosenCharacter() {
        return chosenCharacter;
    }


    public String getPlayerNickname() {
        return playerNickname;
    }


    @Override
    public int getMessageID() {
        return this.messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleLogin(playerNickname,chosenCharacter);
    }
}
