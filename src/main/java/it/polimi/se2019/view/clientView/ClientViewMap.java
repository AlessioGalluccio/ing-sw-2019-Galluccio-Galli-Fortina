package it.polimi.se2019.view.clientView;

import it.polimi.se2019.model.map.Map;

public class ClientViewMap {

    private it.polimi.se2019.model.map.Map mapCopy;

    public ClientViewMap(it.polimi.se2019.model.map.Map mapCopy) {
        this.mapCopy = mapCopy;
    }

    public Map getMapCopy() {

        return mapCopy;
    }


    /**
     *  is used by RMIClient to update the map.
     * @param mapCopy
     */

    public void setMapCopy(Map mapCopy){

        this.mapCopy = mapCopy;
    }


}
