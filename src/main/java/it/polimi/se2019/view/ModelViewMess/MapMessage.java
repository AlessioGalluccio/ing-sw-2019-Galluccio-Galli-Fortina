package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.map.Map;

/**
 * Send a copy of the map to the view
 * Util ONLY at the start of the game, when the view has to know which map rappresent
 * After that use CellMessage!
 */
public class MapMessage implements ModelViewMessage {

    private Map mapCopy;

    public MapMessage(Map mapCopy) {
        this.mapCopy = mapCopy;
    }

    public Map getMapCopy() {
        return mapCopy;
    }
}
