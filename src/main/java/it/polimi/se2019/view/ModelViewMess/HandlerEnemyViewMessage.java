package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.view.remoteView.EnemyView;

/**
 * @author Galli
 */
public interface HandlerEnemyViewMessage {

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of EnemyView in order to handle him correctly.
     * @param e The Enemy View object which has to handle this message
     */
    void handleMessage(EnemyView e);

    /**
     * Each message has an ack in order to handle its receiving correctly.
     * Only if this message's ack is grater the last one received should be handled.
     * This method return the ack of this message.
     * @return the ack of this message.
     */
    int getAck();
}
