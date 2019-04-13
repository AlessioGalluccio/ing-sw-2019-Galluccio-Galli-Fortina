package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;

import java.util.ArrayList;

public abstract class FireMode {
    private ArrayList<ColorRYB> cost;
    private ArrayList<? extends Target> target;
    private String description;
    private int ID;

    public ArrayList<ColorRYB> getCost() {
        return cost;
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public abstract void verify(); //throws some exception

    public abstract void fire();

    public void setTarget(ArrayList<Target> target) {
        this.target = target;
    }
}
