package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;

import java.util.List;

public class RankingMessage implements ModelViewMessage, HandlerPlayerViewMessage  {
    private static final long serialVersionUID = 210769547339910879L;
    private List<Player> ranking;

    public RankingMessage(List<Player> ranking) {
        this.ranking = ranking;
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
     * @param view The View object which has to handle this message
     */
    @Override
    public void handleMessage(View view) {
        try {
            view.handleRankingMessage(ranking);
        }
        catch (Exception e){
            //
        }
    }

    /**
     * Each message has an ack in order to handle its receiving correctly.
     * Since this message is send only one time during a match, its ack can be ignore.
     * This method return the ack of this message.
     * @return the ack of this message.
     */
    @Override
    public int getAck() {
        return -1;
    }
}
