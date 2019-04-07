package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;

public class MapMessage implements ModelViewMessage {

    private Map mapCopy;

    public MapMessage(Map mapCopy) {
        this.mapCopy = mapCopy;
    }

    public Map getMapCopy() {
        return mapCopy;
    }
}
