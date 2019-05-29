package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.view.remoteView.MapView;

/**
 * Send a copy of the map to the view
 * Util ONLY at the start of the game, when the view has to know which map rappresent
 * After that use CellMessage!
 */
public class MapMessage implements ModelViewMessage, HandlerMapViewMessage  {

    private Map mapCopy;

    public MapMessage(Map mapCopy) {
        this.mapCopy = mapCopy;
    }

    public Map getMapCopy() {
        return mapCopy;
    }

    @Override
    public void handleMessage(MapView view) {
        view.handleMapMessage(mapCopy);
    }
}
