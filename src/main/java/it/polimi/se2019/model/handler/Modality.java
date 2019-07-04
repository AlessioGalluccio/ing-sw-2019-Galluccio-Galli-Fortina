package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.player.Player;

/**
 * @author Galli
 */
public interface Modality {

    String CANT_CHOOSE_ACTION = "You can't choose this action. ";

    /**
     * get the object Action by its ID
     * @param actionID the ID of the action
     * @param controller the controller of the player
     * @param gameHandler the gamehandler of the macth
     * @return the Action of that ID
     * @throws WrongInputException if invalid ID
     */
    Action getActionByID(int actionID, Controller controller, GameHandler gameHandler) throws WrongInputException;

    /**
     * get true if frenzy is available, false if not
     * @return true if frenzy is available, false if not
     */
    boolean isFrenzyEnable();

}
