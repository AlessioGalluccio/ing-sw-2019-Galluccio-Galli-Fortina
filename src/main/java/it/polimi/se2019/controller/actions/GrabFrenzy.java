package it.polimi.se2019.controller.actions;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

public class GrabFrenzy extends Grab {

    private static final int MAX_DISTANCE = 3;

    public GrabFrenzy(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    protected int getMaxDistance() {
        return MAX_DISTANCE;
    }
}
