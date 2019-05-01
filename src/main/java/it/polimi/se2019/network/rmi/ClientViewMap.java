package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.map.Map;

public class ClientViewMap {

    private it.polimi.se2019.model.map.Map mapCopy;

    public ClientViewMap(it.polimi.se2019.model.map.Map mapCopy) {
        this.mapCopy = mapCopy;
    }

    public Map getMapCopy(Map mapCopy) {


        return mapCopy;
    }
}
