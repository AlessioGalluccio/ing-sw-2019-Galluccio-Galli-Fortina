package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.deck.Target;

import java.util.ArrayList;

public class PossibleTargetMessage implements ModelViewMessage{
    private ArrayList<Target> possibleTarget;

    public PossibleTargetMessage(ArrayList<Target> possibleTarget) {
        this.possibleTarget = possibleTarget;
    }

    public ArrayList<Target> getPossibleTarget() {
        return possibleTarget;
    }
}
