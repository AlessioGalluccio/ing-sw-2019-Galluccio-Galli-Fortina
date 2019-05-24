package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.model.player.Player;

/**
 * This modality is for the 90% of the game
 * Before the endgame you can have three 'normal' action
 * This modality terminate when the number of skull is zero, at this point may begin the frenzy mode
 */
public class Normal implements Modality {

    public Normal() {
    }

    /**
     * Return the correct action base on modality (normal in this case) and life points of the player
     * @param actionID action which want the user
     * @param author the player linked to the user, this method need to know his damage (adrenaline action vs normal)
     * @param gameHandler
     * @return the correct action based on the life point of the player and the ID
     */
    //For frenzy action go in Frenzy class!
    @Override
    public Action getActionByID(int actionID, Player author, GameHandler gameHandler) {
        return null;
    }

    @Override
    public boolean isFrenzyEnable() {
        return false;
    }
}
