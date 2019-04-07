package it.polimi.se2019.model.player;


import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.ViewControllerMess.StringAndMessage;
import java.util.ArrayList;

public abstract class Action {
    protected GameHandler gameHandler;
    protected ArrayList<StringAndMessage> correctMessages;

    public Action(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void executeAction(Player author, ArrayList<ViewControllerMessage> msg) {

    }
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        return correctMessages;
    }

    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {

        return false; //TODO implementare
    }

}
