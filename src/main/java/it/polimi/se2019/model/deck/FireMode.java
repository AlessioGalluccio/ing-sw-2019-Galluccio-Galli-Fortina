package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;

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
     * @return If the FireMode is free (cost==null) an empty list, else a deep copy of cost
     */
    public List<ColorRYB> getCost() {
        return cost==null ? new ArrayList() : new ArrayList(cost);     //basta una copia dell'array perchè ColorTYB è enum
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isPrimary() {
        return primary;
    }

    public abstract void verify(); //throws some exception

    public abstract void fire();

    public void setTarget(ArrayList<Target> target) {
        this.target = target;
    }
}
