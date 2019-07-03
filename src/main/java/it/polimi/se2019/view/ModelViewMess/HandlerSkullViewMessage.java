package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.view.remoteView.SkullBoardView;

public interface HandlerSkullViewMessage {

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of SkullBoardView in order to handle him correctly.
     * @param s The Skull Board View object which has to handle this message
     */
    void handleMessage(SkullBoardView s);

    /**
     * Each message has an ack in order to handle its receiving correctly.
     * Only if this message's ack os grater the last one received should be handled.
     * This method return the ack of this message.
     * @return the ack of this message.
     */
    int getAck();
}
