package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.WeaponIsLoadedException;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public abstract class Action implements AddActionMethods {
    protected GameHandler gameHandler;
    protected ArrayList<StringAndMessage> correctMessages;
    protected Player playerAuthor;
    protected PlayerView playerView;
    protected Controller controller;

    public Action(GameHandler gameHandler, Controller controller) {
        this.gameHandler = gameHandler;
        this.controller = controller;
        this.playerAuthor = gameHandler.getPlayerByID(controller.getLastReceivedMessage().getAuthorID());
        this.playerView = controller.getLastReceivedMessage().getAuthorView();
    }

    public void executeAction() throws WrongInputException{

    }
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        return correctMessages;
    }

    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {

        return false; //TODO implementare
    }

    public abstract void addCell(int x, int y) throws WrongInputException;

    public abstract void addPlayerTarget(int playerID) throws WrongInputException;

    public abstract void addTargetingScope(int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException;

    public abstract void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException;

    public abstract void addWeapon(int weaponID) throws WrongInputException;

    public abstract void addFiremode(int firemodeID) throws WrongInputException;




}
