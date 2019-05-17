package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public class Shoot extends Action{
    //TODO manca WeaponCardMess per StringAndMessage !!!!!
    private final static StringAndMessage SECOND_STRING_AND_MESS = new StringAndMessage(Identificator.FIRE_MODE_MESSAGE, "Select a Firemode", false);

    public Shoot(GameHandler gameHandler, Controller controller) {
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
    public void addCell(int x, int y) throws IllegalArgumentException {

    }

    @Override
    public void addPlayerTarget(int playerID) throws IllegalArgumentException {

    }

    @Override
    public void addTargetingScope(int targetingCardID) throws NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {

    }

    @Override
    public void addReload(int weaponID) throws IllegalArgumentException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {

    }

    @Override
    public void addWeapon(int weaponID) throws IllegalArgumentException {

    }

    @Override
    public void addFiremode(int firemodeID) throws IllegalArgumentException {

    }
}
