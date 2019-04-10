package it.polimi.se2019.view.ViewControllerMess;


import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.Identificator;

import java.util.ArrayList;

public class TargetMessage extends ViewControllerMessage {
    private int messageID;
    private ArrayList<Target> target;

    public TargetMessage(ArrayList<Target> target) {
        this.target = target;
        this.messageID = Identificator.TARGET_MESSAGE;
    }

    public ArrayList<Target> getTarget() {
        return target;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
