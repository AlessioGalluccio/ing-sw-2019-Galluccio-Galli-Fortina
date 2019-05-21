package it.polimi.se2019.view.remoteView;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MapView implements Observer {

    private Map mapCopy;

    public MapView(Map mapCopy) {
        this.mapCopy = mapCopy;
    }

    public Map getMapCopy() {
        return mapCopy;
    }

    @Override
    public void update(Observable o /*Will be always null*/, Object arg) {

    }
}
