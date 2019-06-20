package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.player.Player;

public interface Modality {

    public static final String CANT_CHOOSE_ACTION = "You can't choose this action. ";

    Action getActionByID(int actionID, Controller controller, GameHandler gameHandler) throws WrongInputException;

    boolean isFrenzyEnable();

}
