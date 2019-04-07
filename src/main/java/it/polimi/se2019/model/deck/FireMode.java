package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import java.util.ArrayList;

public abstract class FireMode extends java.util.Observable {
    private final Color cost;
    private ArrayList<Target> target;
    private final String description;
    private int ID;

    public Color getCost(){

    }

    public int getID() {
        return ID;
    }

    public String getDescription() {

    }

    public abstract void verify(){  //throws some exception

    }

    public abstract void fire() {

    }

    public void setTarget(ArrayList<Target>) {

    }
}
