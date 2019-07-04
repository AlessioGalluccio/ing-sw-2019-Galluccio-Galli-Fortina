package it.polimi.se2019.network.messages;

import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.network.*;

import java.io.Serializable;

/**
 * @author Galli
 * @author Fortina
 */
public class LoginMessage implements HandlerServerMessage, HandlerConfigMessage, Serializable {
    private static final long serialVersionUID = -2692756102145802983L;
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


    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of WaitingRoom in order to handle the login correctly.
     * @param w The waiting room who has to handle this message
     * @param sender The server who send this message
     */
    @Override
    public void handleMessage(WaitingRoom w, Server sender) {
        w.handleLoginMessage(playerNickname, matchID, sender);
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the object which receives this message in order to forward him correctly.
     * @param s The object who has to switch this message
     */
    @Override
    public void handleMessage(SwitchServerMessage s) {
        s.forwardConfigMessage(this);
    }
}
