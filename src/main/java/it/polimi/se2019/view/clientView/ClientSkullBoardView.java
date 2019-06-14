package it.polimi.se2019.view.clientView;

import it.polimi.se2019.model.handler.Death;
import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.HandlerSkullViewMessage;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.ArrayList;

public class ClientSkullBoardView extends SkullBoardView {
    private UiInterface ui;

    public ClientSkullBoardView(UiInterface ui) {
        this.ui = ui;
        ui.setSkullBoard(this);
    }

    @Override
    public void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerSkullViewMessage message = (HandlerSkullViewMessage) arg;
        message.handleMessage(this);
        //ui.printSkullBoard()
    }

}
