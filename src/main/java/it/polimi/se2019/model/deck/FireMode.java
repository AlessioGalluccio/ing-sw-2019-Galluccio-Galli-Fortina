package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.PlayerView;

import java.util.*;

public abstract class FireMode {
    private ArrayList<ColorRYB> cost;
    private ArrayList<? extends Target> target;
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
    public abstract List<Target> sendPossibleTarget(Player player, PlayerView playerView);

    /**
     * Fires to the target set by setTarget()
     */
    public abstract void fire();

    /**
     * Set targets in order to fire it
     * @param target the targets
     */
    public void setTarget(ArrayList<Target> target) {
        this.target = target;
    }
}
