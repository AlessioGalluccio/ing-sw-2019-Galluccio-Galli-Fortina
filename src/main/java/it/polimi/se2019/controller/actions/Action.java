package it.polimi.se2019.controller.actions;


import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public abstract class Action {
    protected GameHandler gameHandler;
    protected ArrayList<StringAndMessage> correctMessages;

    protected Player playerAuthor;
    protected PlayerView playerView;

    public Action(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void executeAction() {

    }
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        return correctMessages;
    }

    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {

        return false; //TODO implementare
    }

    public abstract void addCell(int x, int y) throws IllegalArgumentException;

    public abstract void addPlayerTarget(int playerID) throws IllegalArgumentException;

    public abstract void addTargetingScope(int targetingCardID) throws NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException;

    public abstract void addReload(int weaponID) throws IllegalArgumentException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException;

    public abstract void addWeapon(int weaponID) throws IllegalArgumentException;

    public abstract void addFiremode(int firemodeID) throws IllegalArgumentException;




}
