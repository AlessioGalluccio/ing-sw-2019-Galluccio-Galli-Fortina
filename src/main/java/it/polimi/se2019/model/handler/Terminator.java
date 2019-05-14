package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;

public class Terminator implements Modality {

    private Modality otherMode;

    public Terminator(Modality otherMode) {
        this.otherMode = otherMode;
    }

    public void setOtherMode(Modality m){

    }
    public Modality getOtherMode(){

        return null; //TODO implementare
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
