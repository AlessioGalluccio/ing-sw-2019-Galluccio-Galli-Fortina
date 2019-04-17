package it.polimi.se2019.model.player;

import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

public class GrabFrenzy extends Grab {
    public GrabFrenzy(GameHandler gameHandler) {
        super(gameHandler);
    }

    @Override
    public void executeAction(Player author, ArrayList<ViewControllerMessage> msg) {
        super.executeAction(author, msg);
    }

    @Override
    public ArrayList<StringAndMessage> getStringAndMessageExpected() {
        return super.getStringAndMessageExpected();
    }

    @Override
    public boolean verifyCorrectMessages(Player author, ArrayList<ViewControllerMessage> msg) {
        return super.verifyCorrectMessages(author, msg);
    }
}
