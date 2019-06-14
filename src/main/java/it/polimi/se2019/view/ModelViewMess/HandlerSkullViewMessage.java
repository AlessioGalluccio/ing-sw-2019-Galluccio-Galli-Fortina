package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.view.remoteView.SkullBoardView;

public interface HandlerSkullViewMessage {

    void handleMessage(SkullBoardView s);
    int getAck();
}
