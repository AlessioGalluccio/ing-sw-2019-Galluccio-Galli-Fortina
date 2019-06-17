package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.handler.Death;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.List;

public class SkullBoardMessage implements ModelViewMessage, HandlerSkullViewMessage {
    private static int ID=0;
    private int ack;
    private int numSkullCopy;
    private List<Death> deathCopy;

    public SkullBoardMessage(int numSkullCopy, List<Death> deathCopy) {
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = deathCopy;
        ID++;
        ack=ID;
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

    @Override
    public void handleMessage(Client client) {
        client.forwardToSkullBoardView(this);
    }

    public int getAck() {
        return ack;
    }
}
