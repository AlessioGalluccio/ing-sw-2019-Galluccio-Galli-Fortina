package it.polimi.se2019.controller.actions;


import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.EmptyControllerState;
import it.polimi.se2019.controller.NotYourTurnState;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public abstract class Action implements AddActionMethods {
    protected GameHandler gameHandler;
    protected Player playerAuthor;
    protected PlayerView playerView;
    protected Controller controller;

    public Action(GameHandler gameHandler, Controller controller) {
        this.gameHandler = gameHandler;
        this.controller = controller;
        this.playerAuthor = controller.getAuthor();
        this.playerView = controller.getPlayerView();
    }

    public abstract ArrayList<StringAndMessage> getStringAndMessageExpected();

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public Player getPlayerAuthor() {
        return playerAuthor;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void addPlayerTarget(int playerID) throws WrongInputException {
        throw new WrongInputException();

    }

    @Override
    public void addTargetingScope(int targetingCardID, AmmoBag cost) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException {
        throw new WrongInputException();
    }

    @Override
    public void addReload(int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException {
        throw new WrongInputException();
    }

    @Override
    public void addFireMode(int fireModeID) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addWeapon(WeaponCard weaponCard) throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addOptional(int numOptional) throws WrongInputException, NotEnoughAmmoException  {
        throw new WrongInputException();
    }

    @Override
    public void fire() throws WrongInputException {
        throw new WrongInputException();
    }

    @Override
    public void addDiscardWeapon(WeaponCard weaponCard) throws WrongInputException {
        throw new WrongInputException();
    }

    public abstract AmmoBag getCost();


}
