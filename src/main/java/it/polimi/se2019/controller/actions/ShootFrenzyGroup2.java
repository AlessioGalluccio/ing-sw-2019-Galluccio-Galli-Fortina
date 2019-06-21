package it.polimi.se2019.controller.actions;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;

public class ShootFrenzyGroup2 extends ShootFrenzyGroup1 {

    private static final int DISTANCE_MAX = 2;

    public ShootFrenzyGroup2(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    protected int getMaxDistance() {
        return DISTANCE_MAX;
    }
}
