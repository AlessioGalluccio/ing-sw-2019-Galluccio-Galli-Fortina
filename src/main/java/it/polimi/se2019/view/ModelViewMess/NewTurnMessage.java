package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;

/**
 * @author Galli
 */
public class NewTurnMessage implements ModelViewMessage, HandlerPlayerViewMessage {
    private static final long serialVersionUID = 8095022008364378923L;
    private String nickname;

    public NewTurnMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Only the message itself can't know how to handle itself.
     * This method call the right method of the client who receive this message in order to handle it correctly.
     * @param client The Client object which has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.forwardToClientView(this, "BROADCAST");
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of View in order to handle him correctly.
     * @param p The View object which has to handle this message
     */
    @Override
    public void handleMessage(View p) {
        p.handleTurnMessage(nickname);
    }

    /**
     * Each message has an ack in order to handle its receiving correctly.
     * Since this message dose not modify anything in the receiver object, its ack can be ignore.
     * This method return the ack of this message.
     * @return the ack of this message.
     */
    @Override
    public int getAck() {
        return -1;
    }
}
