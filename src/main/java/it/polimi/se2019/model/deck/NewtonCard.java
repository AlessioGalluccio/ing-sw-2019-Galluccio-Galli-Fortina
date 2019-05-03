package it.polimi.se2019.model.deck;


import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.PlayerView;

import java.util.ArrayList;

public class NewtonCard extends PowerupCard{


    public NewtonCard(ColorRYB color) {
        super(color);
    }

    @Override
    public void useCard(Player author) {

    }

    @Override
    public ArrayList<Target> sendPossibleTarget(Player player, PlayerView playerView) {
        return null;
    }
}