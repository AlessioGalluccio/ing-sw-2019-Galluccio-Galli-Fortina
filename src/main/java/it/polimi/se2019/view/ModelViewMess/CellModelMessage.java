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

    public Cell getCellCopy() {
        return cellCopy;
    }

    @Override
    public void handleMessage(MapView view) {
        view.handleCellMessage(cellCopy);
    }

    @Override
    public void handleMessage(Client client) {
        client.forwardToMapView(this);
    }

    public int getAck() {
        return ack;
    }

    @Override
    public int getX() {
        return cellCopy.getCoordinateX();
    }

    @Override
    public int getY() {
        return cellCopy.getCoordinateY();
    }
}
