package it.polimi.se2019.view;

import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ModelViewMess.StartGameMessage;

import java.util.Observer;

/**
 * Common class between Client View and Remote View
 * Observer of network handler and model
 * Observable by network handler and controller
 */
public abstract class View extends Observable implements Observer {

    public abstract void printFromController(String string);

    public abstract void handleStartGameMessage(StartGameMessage startGameMessage);

    public abstract void handlePlayerMessage(Player p);
}
