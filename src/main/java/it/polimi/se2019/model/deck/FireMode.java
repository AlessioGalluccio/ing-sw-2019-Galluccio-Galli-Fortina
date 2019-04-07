package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import java.util.ArrayList;

public abstract class FireMode {
    private final Color cost;
    private ArrayList<Target> target;
    private final String description;
    private int ID;

    public Color getCost(){
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
