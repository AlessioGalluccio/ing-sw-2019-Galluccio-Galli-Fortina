package it.polimi.se2019.view.remoteView;

import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.view.ModelViewMess.HandlerMapViewMessage;
import it.polimi.se2019.model.Observable;

import java.util.Observer;

public class MapView extends Observable implements Observer {

    private Map mapCopy;
    private Cell[][] cells;

    public MapView(Map mapCopy) {
        this.mapCopy = mapCopy;
    }

    public Map getMapCopy() {
        return mapCopy;
    }

    @Override
    public void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerMapViewMessage message = (HandlerMapViewMessage) arg;
        message.handleMessage(this);
        notifyObservers(message); //Forward message to client
    }

    public void handleMapMessage(Map m) {
        mapCopy = m;
        cells = m.getCell();
    }

    public void handleCellMessage(Cell c) {
        cells[c.getCoordinateX()][c.getCoordinateY()] = c;
    }
}
