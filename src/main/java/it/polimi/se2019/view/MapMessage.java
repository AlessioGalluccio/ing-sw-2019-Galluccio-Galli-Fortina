package it.polimi.se2019.view;

import it.polimi.se2019.model.map.Map;

public class MapMessage implements ModelViewMessage {

    private Map mapCopy;

    public MapMessage(Map mapCopy) {
        this.mapCopy = mapCopy;
    }

    public Map getMapCopy() {
        return mapCopy;
    }
}
