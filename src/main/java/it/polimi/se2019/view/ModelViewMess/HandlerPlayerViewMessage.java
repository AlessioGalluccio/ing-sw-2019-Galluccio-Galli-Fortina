package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.view.View;

public interface HandlerPlayerViewMessage {

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of View in order to handle him correctly.
     * @param p The View object which has to handle this message
     */
    void handleMessage(View p);

    /**
     * Each message has an ack in order to handle its receiving correctly.
     * Only if this message's ack is grater the last one received should be handled.
     * This method return the ack of this message.
     * @return the ack of this message.
     */
    int getAck();
}