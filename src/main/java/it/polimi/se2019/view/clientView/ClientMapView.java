package it.polimi.se2019.view.clientView;

import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.HandlerMapViewMessage;
import it.polimi.se2019.view.remoteView.MapView;

public class ClientMapView extends MapView {
    private UiInterface ui;
    private int lastAck;

    public ClientMapView(UiInterface ui) {
        this.ui = ui;
        ui.setMapView(this);
    }


    /**
     * This method is called whenever the map object or one of his cells is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param   o     Always null
     * @param   arg   The message with which update the map
     */
    @Override
    public synchronized void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerMapViewMessage message = (HandlerMapViewMessage) arg;
        if(message.getAck()>lastAck) {
            message.handleMessage(this);
            notifyAll();
            // synchronized(ui) ui.printMap();
        }
    }
}
