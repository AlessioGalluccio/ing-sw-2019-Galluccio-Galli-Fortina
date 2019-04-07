package it.polimi.se2019.view;

import it.polimi.se2019.model.handler.Death;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SkullBoardView implements Observer {

    @Override
    public void update(Observable o, Object arg) {

    }

    private int numSkullCopy;
    private ArrayList<Death> deathCopy;

    public SkullBoardView(int numSkullCopy, ArrayList<Death> deathCopy) {
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = deathCopy;
    }

    public ArrayList<Death> getDeathCopy() {
        return deathCopy;
    }

    public int getNumSkullCopy() {
        return numSkullCopy;
    }
}
