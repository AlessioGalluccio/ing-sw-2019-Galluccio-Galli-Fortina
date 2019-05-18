package it.polimi.se2019.model.deck;

import it.polimi.se2019.controller.actions.AddActionMethods;
import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.firemodes.AddFireModeMethods;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.*;

public abstract class FireMode implements AddFireModeMethods {
    private ArrayList<ColorRYB> cost;
    private transient ArrayList<? extends Target> target;
    private String description;
    private int ID;
    private boolean used;
    private boolean primary;

    /**
     * Create a deep copy of cost
     * @return If the FireMode is free an empty list, else a deep copy of cost
     */
    public List<ColorRYB> getCost() {
        return cost==null ? new ArrayList<>() : new ArrayList<>(cost);     //basta una copia dell'array perchè ColorTYB è enum
    }

    /**
     *
     * @return Fire mode's ID
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @return Fire mode's description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return true if it has been already used in this turn, else false
     */
    public boolean isUsed() {
        return used;
    }

    /**
     *
     * @return true if it is a primary fire mode (compulsory to use), else false
     */
    public boolean isPrimary() {
        return primary;
    }

    /**
     * Create and send message containing the possible targets to the view of player
     * @param player the player who wants to use this fire mode
     * @param playerView receiver of the message
     * @return the possible target that player can hit, return null if there is no target
     */
    public abstract List<Target> sendPossibleTarget(Player player, PlayerView playerView, GameHandler gameHandler);

    /**
     * Fires to the target set by setTarget(
     * @param stack the correct messages sent by the Player
     * @param gameHandler the handler of the match
     */
    public abstract void fire(List<ViewControllerMessage> stack, GameHandler gameHandler);

    /**
     *
     * @param stack the messages sent by the Player. On ly the last one can be incorrect
     * @param gameHandler the handler of the match
     * @return true if the last message is correct, false if not
     * @throws NotEnoughAmmoException if the Player doesn't have enough ammo for the firemode
     */
    public abstract boolean controlMessage(List<ViewControllerMessage> stack, GameHandler gameHandler) throws NotEnoughAmmoException;

    /**
     * Set targets in order to fire it
     * @param target the targets
     */
    public void setTarget(ArrayList<Target> target) {
        this.target = target;
    }

    public abstract List<StringAndMessage> getMessageListExpected();

    public abstract boolean giveOnlyMarks();

    @Override
    public abstract void addCell(Shoot shoot, int x, int y) throws WrongInputException;

    @Override
    public abstract void addPlayerTarget(Shoot shoot, int playerID) throws WrongInputException;

    @Override
    public abstract void addTargetingScope(Shoot shoot, int targetingCardID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, FiremodeOfOnlyMarksException;

    @Override
    public abstract void addReload(Shoot shoot, int weaponID) throws WrongInputException, NotPresentException, NotEnoughAmmoException, WeaponIsLoadedException;

    @Override
    public abstract void addWeapon(Shoot shoot, int weaponID) throws WrongInputException;

}
