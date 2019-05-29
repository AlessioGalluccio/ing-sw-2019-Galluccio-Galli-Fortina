package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.handler.Death;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.List;

public class SkullBoardMessage implements ModelViewMessage, HandlerSkullViewMessage {

    private int numSkullCopy;
    private List<Death> deathCopy;

    public SkullBoardMessage(int numSkullCopy, List<Death> deathCopy) {
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = deathCopy;
    }

    public int getNumSkullCopy() {
        return numSkullCopy;
    }

    public List<Death> getDeathCopy() {
        return deathCopy;
    }

    @Override
    public void handleMessage(SkullBoardView s) {
        s.handleSkullMessage(numSkullCopy, deathCopy);
    }
}
