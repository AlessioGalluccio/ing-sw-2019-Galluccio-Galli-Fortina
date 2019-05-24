package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;

public class Frenzy implements Modality {

    public Frenzy() {
    }

    /**
     * Return the correct action base on modality (frenzy in this case) and life points of the player
     * @param actionID action which want the user
     * @param author the player linked to the user, this method need to know his damage (adrenaline action vs normal)
     * @param gameHandler this method need to know if the player is before or after the first player
     * @return the correct action based on the life point of the player, his position on the turn list and the ID
     */
    //For no-frenzy action go in Normal class!
    @Override
    public Action getActionByID(int actionID, Player author, GameHandler gameHandler) {
        //TODO
        return null;
    }

    @Override
    public boolean isFrenzyEnable() {
        return true;
    }
}

