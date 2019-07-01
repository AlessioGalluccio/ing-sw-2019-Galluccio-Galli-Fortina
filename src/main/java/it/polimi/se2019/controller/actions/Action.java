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

    /**
     * constructor
     * @param gameHandler the gamehandler of the match
     * @param controller the controller of the player
     */
    public Action(GameHandler gameHandler, Controller controller) {
        this.gameHandler = gameHandler;
        this.controller = controller;
        this.playerAuthor = controller.getAuthor();
        this.playerView = controller.getPlayerView();
    }

    /**
     * get the string and messages needed for the action
     * @return an ArrayList of StringAndMessage of the input and requests of this action
     */
    public abstract ArrayList<StringAndMessage> getStringAndMessageExpected();

    /**
     * get the gamehandler of this match
     * @return the GameHandler of this match
     */
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    /**
     * get the author Player of this action
     * @return the author Player of this action
     */
    public Player getPlayerAuthor() {
        return playerAuthor;
    }

    /**
     * get the PlayerView of the author player
     * @return the PlayerView if the author
     */
    public PlayerView getPlayerView() {
        return playerView;
    }

    /**
     * get the Controller of the author player
     * @return the Controller of the author player
     */
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

    /**
     * get the cost of the entire action, optional effects included
     * @return the AmmoBag cost of the entire action
     */
    public abstract AmmoBag getCost();


}
