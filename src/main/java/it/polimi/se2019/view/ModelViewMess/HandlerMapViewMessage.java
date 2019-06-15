package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.view.remoteView.MapView;

public interface HandlerMapViewMessage {
    void handleMessage(MapView view);
    int getAck();
    int getX();
    int getY();
}
