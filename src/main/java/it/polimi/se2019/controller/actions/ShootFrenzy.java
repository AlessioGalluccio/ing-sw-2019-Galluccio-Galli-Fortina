package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

public class ShootFrenzy extends Shoot {
    public ShootFrenzy(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    public void executeAction() throws WrongInputException {
        super.executeAction();
    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        return super.getStringAndMessageExpected();
    }

    @Override
    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {
        return super.verifyCorrectMessages(author, msg);
    }

    @Override
    public void addCell(int x, int y) throws WrongInputException {
        super.addCell(x, y);
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        super.addPlayerTarget(playerID);
    }

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        super.addTargetingScope(targetingCardID, cost);
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        super.addReload(weaponID);
    }

    @Override
    public void addFireMode(int fireModeID) throws WrongInputException {
        super.addFireMode(fireModeID);
    }

    @Override
    public void addWeapon(WeaponCard weaponCard) throws WrongInputException {
        super.addWeapon(weaponCard);
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException {
        super.addOptional(numOptional);
    }

    @Override
    public void addNope() throws WrongInputException {
        super.addNope();
    }

    @Override
    public void fire() throws WrongInputException {
        super.fire();
    }
}
