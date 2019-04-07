package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.model.deck.FireMode;

/**
 * FireMode for move player up to two cells, can be used only once
 */
public class RocketLauncher_2 extends FireMode {
    private boolean used;

    public boolean isUsed(){
        return used;
    }

    @Override
    public void verify() {

    }

    @Override
    public void fire() {

    }
}
