package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.view.remoteView.MapView;

public interface HandlerMapViewMessage {

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of MapView  in order to handle him correctly.
     * @param view The Map View object which has to handle this message
     */
    void handleMessage(MapView view);

    /**
     * Each message has an ack in order to handle its receiving correctly.
     * Only if this message's ack is grater the last one received should be handled.
     * This method return the ack of this message.
     * @return the ack of this message.
     */
    int getAck();
    int getX();
    int getY();
}
