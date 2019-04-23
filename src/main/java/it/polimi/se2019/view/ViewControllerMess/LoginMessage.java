package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.Character;

public class LoginMessage extends ViewControllerMessage{

    private int messageID;
    private String playerNickname;
    private Character choosenCharacter;

    public LoginMessage(String playerNickname, Character choosenCharacter) {

        this.messageID = Identificator.LOGIN_MESSAGE;

        this.playerNickname = playerNickname;

        this.choosenCharacter = choosenCharacter;
    }

    public Character getChoosenCharacter() {
        return choosenCharacter;
    }


    public String getPlayerNickname() {
        return playerNickname;
    }


    @Override
    public int getMessageID() {
        return this.messageID;
    }
}
