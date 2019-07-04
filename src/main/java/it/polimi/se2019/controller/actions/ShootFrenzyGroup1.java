package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

/**
 * @author Galluccio
 */
public class ShootFrenzyGroup1 extends ShootAdrenaline {

    private static final int MAX_DISTANCE = 1;

    //errors
    public static final String CANT_DO_RELOAD = "You can't reload now. ";

    /**
     * constructor
     * @param gameHandler the gamehandler of the match
     * @param controller the controller of the player
     */
    public ShootFrenzyGroup1(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }


    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        if(this.weapon == null){
            playerAuthor.loadWeapon(weaponID);
        }
        else{
            throw new WrongInputException(CANT_DO_RELOAD);
        }
    }

    @Override
    protected int getMaxDistance() {
        return MAX_DISTANCE;
    }
}
