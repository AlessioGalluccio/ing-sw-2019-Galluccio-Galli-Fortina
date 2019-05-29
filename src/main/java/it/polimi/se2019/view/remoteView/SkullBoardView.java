package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.handler.Death;
import it.polimi.se2019.view.ModelViewMess.HandlerSkullViewMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class SkullBoardView extends Observable implements Observer {

    private int numSkullCopy;
    private ArrayList<Death> deathCopy;

    public SkullBoardView(int numSkullCopy, ArrayList<Death> deathCopy) {
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = deathCopy;
    }

    public ArrayList<Death> getDeathCopy() {
        return deathCopy;
    }

    public int getNumSkullCopy() {
        return numSkullCopy;
    }

    @Override
    public void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerSkullViewMessage message = (HandlerSkullViewMessage) arg;
        message.handleMessage(this);
        notifyObservers(message); //Forward message to client
    }

    public void handleSkullMessage(int numSkullCopy, List<Death> deathCopy) {
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = (ArrayList<Death>) deathCopy;
    }
}
