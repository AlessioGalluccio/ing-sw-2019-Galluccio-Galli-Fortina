package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.remoteView.MapView;

public class CellModelMessage implements ModelViewMessage, HandlerMapViewMessage  {
    private static final long serialVersionUID = -2405952324047251480L;
    private static int ID=0;
    private int ack;
    private Cell cellCopy;

    public CellModelMessage(Cell cellCopy) {
        this.cellCopy = cellCopy;
        ID++;
        ack=ID;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the mapView who receive this message in order to handle it.
     * @param view The MapView object which has to handle this message
     */
    @Override
    public void handleMessage(MapView view) {
        view.handleCellMessage(cellCopy);
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the client who receive this message in order to forward it correctly.
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
     * Get the X of the cell in this message
     * @return the X of the cell in this message
     */
    @Override
    public int getX() {
        return cellCopy.getCoordinateX();
    }

    /**
     * Get the Y of the cell in this message
     * @return the Y of the cell in this message
     */
    @Override
    public int getY() {
        return cellCopy.getCoordinateY();
    }
}
