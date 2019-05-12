package it.polimi.se2019.view.ViewControllerMess;


import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public class TargetMessage extends ViewControllerMessage {

    private int messageID;
    private Target target;
    private int authorID;
    private PlayerView authorView;


    //TODO classe da eliminare!

    /**
     * TargetMessage class's constructor
     * @param target
     * @param authorID
     * @param authorView
     */


    public TargetMessage(Target target, int authorID, PlayerView authorView) {

        this.target = target;
        this.messageID = Identificator.TARGET_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    public Target getTarget() {
        return target;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        //TODO
    }
}
