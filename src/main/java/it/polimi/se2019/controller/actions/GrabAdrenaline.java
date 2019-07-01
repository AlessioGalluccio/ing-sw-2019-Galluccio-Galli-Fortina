package it.polimi.se2019.controller.actions;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

public class GrabAdrenaline extends Grab {

    //the only change between GrabAdrenaline and Grab
    private static final int DISTANCE_MAX = 2;

    /**
     * constructor
     * @param gameHandler the gamehandler of the match
     * @param controller the controller of the player
     */
    public GrabAdrenaline(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    protected int getMaxDistance() {
        return DISTANCE_MAX;
    }
}
