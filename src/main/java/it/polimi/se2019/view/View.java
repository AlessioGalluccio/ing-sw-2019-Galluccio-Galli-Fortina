package it.polimi.se2019.view;

import it.polimi.se2019.model.Observable;
import java.util.Observer;

/**
 * Common class between Client View and Remote View
 * Observer of network handler and model
 * Observable by network handler and controller
 */
public abstract class View extends Observable implements Observer {

    public abstract void printFromController(String string);
}
