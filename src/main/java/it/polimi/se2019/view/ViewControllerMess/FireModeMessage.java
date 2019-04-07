package it.polimi.se2019.view.ViewControllerMess;

public class FireModeMessage extends ViewControllerMessage {

    private int firemodeID;

    public FireModeMessage(int firemodeID) {
        this.firemodeID = firemodeID;
    }

    public int getFiremodeID() {
        return firemodeID;
    }
}
