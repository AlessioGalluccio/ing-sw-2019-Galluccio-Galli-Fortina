package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.handler.Death;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.List;

/**
 * @author Galli
 * @author Fortina
 */
public class SkullBoardMessage implements ModelViewMessage, HandlerSkullViewMessage {
    private static final long serialVersionUID = -6227107342873236285L;
    private static int ID=0;
    private int ack;
    private int originalSkull;
    private int numSkullCopy;
    private List<Death> deathCopy;

    public SkullBoardMessage(int originalSkull, int numSkullCopy, List<Death> deathCopy) {
        this.originalSkull = originalSkull;
        this.numSkullCopy = numSkullCopy;
        this.deathCopy = deathCopy;
        ID++;
        ack=ID;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of SkullBoardView in order to handle him correctly.
     * @param s The Skull Board View object which has to handle this message
     */
    @Override
    public void handleMessage(SkullBoardView s) {
        s.handleSkullMessage(originalSkull, numSkullCopy, deathCopy);
    }

    /**
     * Only the message itself can't know how to handle itself.
     * This method call the right method of the client who receive this message in order to forward it to the MapView object.
     * @param client The Client object which has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.forwardToSkullBoardView(this);
    }

    /**
     * Each message has an ack in order to handle its receiving correctly.
     * Only if this message's ack is grater the last one received should be handled.
     * This method return the ack of this message.
     * @return the ack of this message.
     */
    public int getAck() {
        return ack;
    }
}
