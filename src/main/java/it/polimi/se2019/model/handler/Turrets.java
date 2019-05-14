package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;

public class Turrets implements Modality {

    public Turrets() {
    }

    @Override
    public int getNumSkulls() {
        return 0;
    }

    @Override
    public Action getActionByID(int actionID, Player author, GameHandler gameHandler) {
        return null;
    }

    @Override
    public boolean isFrenzyEnabled() {
        return false;
    }
}
