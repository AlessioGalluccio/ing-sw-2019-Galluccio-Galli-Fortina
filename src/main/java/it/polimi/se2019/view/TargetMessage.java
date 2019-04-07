package it.polimi.se2019.view;


import java.util.ArrayList;

public class TargetMessage extends ViewControllerMessage {

    private ArrayList<Target> target;

    public TargetMessage(ArrayList<Target> target) {
        this.target = target;
    }

    public ArrayList<Target> getTarget() {
        return target;
    }
}
