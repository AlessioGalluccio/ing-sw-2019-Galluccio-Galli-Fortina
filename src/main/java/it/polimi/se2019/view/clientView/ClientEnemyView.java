package it.polimi.se2019.view.clientView;

import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.HandlerEnemyViewMessage;
import it.polimi.se2019.view.remoteView.EnemyView;

public class ClientEnemyView extends EnemyView {
    private UiInterface ui;

    public ClientEnemyView(String nickname) {
        super(nickname);
    }

    @Override
    public void update(java.util.Observable o/*Will be always null*/, Object arg) {
        HandlerEnemyViewMessage message = (HandlerEnemyViewMessage) arg;
        message.handleMessage(this);
        //ui.printEnemyView()
    }

    public void setUi(UiInterface ui) {
        this.ui = ui;
    }
}
