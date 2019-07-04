package it.polimi.se2019.controller.actions;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;

/**
 * @author Galluccio
 */
public class ShootFrenzyGroup2 extends ShootFrenzyGroup1 {

    private static final int DISTANCE_MAX = 2;

    /**
     * constructor
     * @param gameHandler the gamehandler of the match
     * @param controller the controller of the player
     */
    public ShootFrenzyGroup2(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    protected int getMaxDistance() {
        return DISTANCE_MAX;
    }
}
