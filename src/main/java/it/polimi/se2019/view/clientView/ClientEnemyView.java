package it.polimi.se2019.view.clientView;

import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.HandlerEnemyViewMessage;
import it.polimi.se2019.view.remoteView.EnemyView;

public class ClientEnemyView extends EnemyView {
    private UiInterface ui;
    private int lastAck;

    public ClientEnemyView(String nickname, UiInterface ui) {
        super(nickname);
        this.ui = ui;
        ui.setEnemyView(this);
    }

    /**
     * This method is called whenever the player object of this enemy is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param   o     Always null
     * @param   arg   The message with which update the enemy board
     */
    @Override
    public synchronized void update(java.util.Observable o/*Will be always null*/, Object arg) {
        HandlerEnemyViewMessage message = (HandlerEnemyViewMessage) arg;
        if(message.getAck()>lastAck) {
            lastAck = message.getAck();
            message.handleMessage(this);
            synchronized(ui) {
                ui.updateEnemy(this);
            }
        }
    }

    public void setUi(UiInterface ui) {
        this.ui = ui;
        ui.setEnemyView(this);
    }
}
