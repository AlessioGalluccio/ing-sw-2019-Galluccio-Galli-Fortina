package it.polimi.se2019.view.ModelViewMess;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.View;

import java.util.List;

public class RankingMessage implements ModelViewMessage, HandlerPlayerViewMessage  {
    private List<Player> ranking;

    public RankingMessage(List<Player> ranking) {
        this.ranking = ranking;
    }

    @Override
    public void handleMessage(Client client) {
        client.forwardToClientView(this, "BROADCAST");
    }

    @Override
    public void handleMessage(View view) {
        view.handleRankingMessage(ranking);
    }

    @Override
    public int getAck() {
        return -1;
    }
}
