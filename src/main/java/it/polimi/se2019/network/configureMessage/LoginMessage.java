package it.polimi.se2019.network.configureMessage;

import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.network.*;

public class LoginMessage implements HandlerServerMessage, HandlerConfigMessage {
    private int messageID;
    private int matchID;
    private String playerNickname;

    /**
     * Create a new login message if the player knows the match's id
     * @param playerNickname the name chosen by the user
     * @param matchID Match's ID to join
     */
    public LoginMessage(String playerNickname, int matchID) {
        this.messageID = Identificator.LOGIN_MESSAGE;
        this.playerNickname = playerNickname;
        this.matchID = matchID;
    }

    /**
     * Create a new login message if the player doesn't know the match's id
     * @param playerNickname the name chosen by the user
     */
    public LoginMessage(String playerNickname) {
        this.messageID = Identificator.LOGIN_MESSAGE;
        this.playerNickname = playerNickname;
        this.matchID = -1;
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
