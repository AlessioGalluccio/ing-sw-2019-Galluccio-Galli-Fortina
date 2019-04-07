package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.handler.Death;
import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;

import java.util.ArrayList;

public class SkullBoardMessage implements ModelViewMessage {

    private int numSkullCopy;
    private ArrayList<Death> deathCopy;

    public SkullBoardMessage(int numSkullCopy, ArrayList<Death> deathCopy) {
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = deathCopy;
    }

    public int getNumSkullCopy() {
        return numSkullCopy;
    }

    public ArrayList<Death> getDeathCopy() {
        return deathCopy;
    }
}