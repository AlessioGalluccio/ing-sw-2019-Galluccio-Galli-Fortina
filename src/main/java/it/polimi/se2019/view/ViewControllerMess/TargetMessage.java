package it.polimi.se2019.view.ViewControllerMess;


import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.Identificator;

import java.util.ArrayList;

public class TargetMessage extends ViewControllerMessage {
    private int messageID;

    //TODO modificare questo ArrayList in un possibile singolo target
    private Target target;

    public TargetMessage(Target target) {
        this.target = target;
        this.messageID = Identificator.TARGET_MESSAGE;
    }

    public Target getTarget() {
        return target;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
