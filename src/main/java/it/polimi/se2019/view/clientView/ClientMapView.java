package it.polimi.se2019.view.clientView;

import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.HandlerMapViewMessage;
import it.polimi.se2019.view.remoteView.MapView;

public class ClientMapView extends MapView {
    private UiInterface ui;

    public ClientMapView(UiInterface ui) {
        this.ui = ui;
        ui.setMapView(this);
    }


    @Override
    public void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerMapViewMessage message = (HandlerMapViewMessage) arg;
        message.handleMessage(this);
        //ui.printMap();
    }
}
