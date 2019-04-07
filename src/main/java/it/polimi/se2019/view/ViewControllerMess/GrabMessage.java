package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.player.Grab;

public class GrabMessage extends ViewControllerMessage {

    private Grab grab;

    public GrabMessage(Grab grab) {
        this.grab = grab;
    }

    public Grab getGrab() {
        return grab;
    }
}
