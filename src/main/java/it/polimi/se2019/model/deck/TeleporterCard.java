package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

public class TeleporterCard extends PowerupCard{


    public TeleporterCard(ColorRYB color) {
        super(color);
    }

    @Override
    public void useCard(Player author) {

    }

    @Override
    public void reloadAmmo(Player p) {

    }
}
