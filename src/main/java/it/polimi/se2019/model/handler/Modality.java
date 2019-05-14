package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;

public interface Modality {

    int getNumSkulls();
    Action getActionByID(int actionID, Player author, GameHandler gameHandler);
    boolean isFrenzyEnabled();

}
