package it.polimi.se2019.controller.actions;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public class GrabFrenzy extends Grab {
    public GrabFrenzy(GameHandler gameHandler, Controller controller) {
        super(gameHandler, controller);
    }

    @Override
    public void executeAction() {
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
    public void addTargetingScope(int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        super.addTargetingScope(targetingCardID);
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        super.addReload(weaponID);
    }

    @Override
    public void addWeapon(int weaponID) throws WrongInputException {
        super.addWeapon(weaponID);
    }

    @Override
    public void addFiremode(int firemodeID) throws WrongInputException {
        super.addFiremode(firemodeID);
    }
}
