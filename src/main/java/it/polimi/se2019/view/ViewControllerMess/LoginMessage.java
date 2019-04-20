package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.player.Character;

public class LoginMessage extends ViewControllerMessage{

    private int messageID;
    private String playerNickname;
    private Character choosenCharacter;

    public LoginMessage(int messageID, String playerNickname, Character choosenCharacter) {

        this.messageID = messageID;

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
