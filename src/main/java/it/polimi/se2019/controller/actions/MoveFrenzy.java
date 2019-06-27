package it.polimi.se2019.controller.actions;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;

public class MoveFrenzy extends Move {

    private static final int MAX_DISTANCE = 4;

    public MoveFrenzy(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    protected int getMaxDistance() {
        return MAX_DISTANCE;
    }
}
