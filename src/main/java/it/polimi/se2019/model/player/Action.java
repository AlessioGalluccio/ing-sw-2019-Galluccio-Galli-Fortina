package it.polimi.se2019.model.player;


import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.ViewControllerMess.StringAndMessage;
import java.util.ArrayList;

public abstract class Action {
    protected GameHandler gameHandler;
    protected ArrayList<ViewControllerMessage> correctMessages;

    public void executeAction(Player author, ArrayList<ViewControllerMessage> msg) {

    }
    public StringAndMessage getStringAndMessageExpected() {

    }

    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {

    }

}
