package it.polimi.se2019.network.configureMessage;

import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.network.*;

public class LoginMessage implements HandlerServerMessage, HandlerConfigMessage {
    private int messageID;
    private int matchID;
    private String playerNickname;
    private Character chosenCharacter;

    /**
     * Create a new login message
     * @param playerNickname the name chosen by the user
     */
    public LoginMessage(String playerNickname, int matchID) {
        this.messageID = Identificator.LOGIN_MESSAGE;
        this.playerNickname = playerNickname;
        this.matchID = matchID;
    }

    public Character getChoosenCharacter() {
        return chosenCharacter;
    }


    public String getPlayerNickname() {
        return playerNickname;
    }


    @Override
    public void handleMessage(WaitingRoom w, Server sender) {
        w.handleLoginMessage(playerNickname, matchID, sender);
    }

    @Override
    public void handleMessage(SwitchServerMessage s) {
        s.forwardConfigMessage(this);
    }
}
