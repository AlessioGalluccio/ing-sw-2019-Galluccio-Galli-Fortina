package it.polimi.se2019.view.clientView;

import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.HandlerSkullViewMessage;
import it.polimi.se2019.view.remoteView.SkullBoardView;


public class ClientSkullBoardView extends SkullBoardView {
    private UiInterface ui;
    private int lastAck;

    public ClientSkullBoardView(UiInterface ui) {
        this.ui = ui;
        ui.setSkullBoard(this);
    }

    /**
     * This method is called whenever the array of Death object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param   o     Always null
     * @param   arg   The message with which update the skull board
     */
    @Override
    public synchronized void update(java.util.Observable o /*Will be always null*/, Object arg) {
        HandlerSkullViewMessage message = (HandlerSkullViewMessage) arg;
        if(message.getAck()>=lastAck) {
            lastAck = message.getAck();
            message.handleMessage(this);
            synchronized(ui) {
                ui.updateSkullBoard();
            }
        }
    }

}
