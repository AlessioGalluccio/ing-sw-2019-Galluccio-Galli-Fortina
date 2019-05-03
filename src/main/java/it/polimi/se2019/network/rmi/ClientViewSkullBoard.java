package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.handler.Death;

import java.util.ArrayList;

public class ClientViewSkullBoard {

    private int numSkullCopy;
    private ArrayList<Death> deathCopy;

    public ClientViewSkullBoard(int numSkullCopy, ArrayList<Death> deathCopy) {
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
