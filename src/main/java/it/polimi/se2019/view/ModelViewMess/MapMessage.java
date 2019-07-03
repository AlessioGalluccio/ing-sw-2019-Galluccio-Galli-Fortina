package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.remoteView.MapView;

/**
 * Send a copy of the map to the view
 * Util ONLY at the start of the game, when the view has to know which map represent
 * After that use CellMessage!
 */
public class MapMessage implements ModelViewMessage, HandlerMapViewMessage  {
    private static final long serialVersionUID = -5176554593125828697L;
    private static int ID=0;
    private int ack;
    private Map mapCopy;

    public MapMessage(Map mapCopy) {
        this.mapCopy = mapCopy;
        ID++;
        ack=ID;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of MapView in order to handle it correctly.
     * @param view The Map View object which has to handle this message
     */
    @Override
    public void handleMessage(MapView view) {
        view.handleMapMessage(mapCopy);
    }

    /**
     * Only the message itself can't know how to handle itself.
     * This method call the right method of the client who receive this message in order to forward it to the MapView object.
     * @param client The Client object which has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.forwardToMapView(this);
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

    /**
     * Generally, get the X of the cell in this message.
     * Since this message contain the whole map and not just a cell, the X can be ignore.
     * @return the X of the cell in this message
     */
    @Override
    public int getX() {
        return 0;
    }

    /**
     * Generally, get the Y of the cell in this message.
     * Since this message contain the whole map and not just a cell, the Y can be ignore.
     * @return the Y of the cell in this message
     */
    @Override
    public int getY() {
        return 0;
    }
}
