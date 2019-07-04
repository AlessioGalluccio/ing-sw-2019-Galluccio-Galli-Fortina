package it.polimi.se2019.view;

import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;

import java.util.List;
import java.util.Observer;

/**
 * Common class between Client View and Remote View
 * Observer of network handler and model
 * Observable by network handler and controller
 */
public abstract class View extends Observable implements Observer {

    /**
     * Receive a string message from the controller and forward it to the networkHandler
     * @param string the message
     */
    public abstract void printFromController(String string);

    /**
     * Set the attribute playerCopy to the new playerCopy
     * @param p the new playerCopy
     */
    public abstract void handlePlayerMessage(Player p);

    /**
     * This method is call by a NewTurnMessage object.
     * It notify the user that a new turn is begin
     * @param nickname the player's nickname of the turn
     */
    public abstract void handleTurnMessage(String nickname);

    /**
     * This method is call by a RankingMessage object.
     * It forward to the ui the ranking of the game
     * @param ranking the ranking
     * @throws Exception if something goes wrong on GUI
     */
    public abstract void handleRankingMessage(List<Player> ranking) throws Exception;

    /**
     * set possible character for the player
     * @param characters list of possible character
     */
    public abstract void setPossibleCharacter(List<Character> characters);
}
