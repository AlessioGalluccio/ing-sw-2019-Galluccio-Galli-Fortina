package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;

public class Frenzy  implements Modality {
    private int numSkulls;
    private boolean isfrenzyEnabled;

    public Frenzy(int numSkulls){
        this.isfrenzyEnabled = true;
        this.numSkulls = numSkulls;
    }

    @Override
    public int getNumSkulls() {
        return numSkulls;
    }

    @Override
    public Action getActionByID(int actionID, Player author, GameHandler gameHandler) {
        //TODO
        return null;
    }

    @Override
    public boolean isFrenzyEnabled() {
        return isfrenzyEnabled;
    }
}
