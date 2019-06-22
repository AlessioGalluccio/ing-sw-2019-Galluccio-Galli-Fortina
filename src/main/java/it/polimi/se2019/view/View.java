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

    public abstract void printFromController(String string);

    public abstract void handlePlayerMessage(Player p);

    public abstract void handleTurnMessage(String nickname);

    public abstract void handleRankingMessage(List<Player> ranking) throws Exception;

    public abstract void setPossibleCharacter(List<Character> characters);
}
