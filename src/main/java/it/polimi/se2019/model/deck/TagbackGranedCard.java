package it.polimi.se2019.model.deck;


import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public class TagbackGranedCard extends PowerupCard{


    public TagbackGranedCard(ColorRYB color, int id, int IDtype) {
        super(color, id, IDtype);
    }

    @Override
    public void useCard(Player author) {

    }

    @Override
    public ArrayList<Target> sendPossibleTarget(Player player, PlayerView playerView) {
        return null;
    }
}