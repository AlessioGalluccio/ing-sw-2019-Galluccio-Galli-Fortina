package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

import java.util.ArrayList;

public class TeleporterCard extends PowerupCard{


    public TeleporterCard(ColorRYB color) {
        super(color);
    }

    @Override
    public void useCard() {

    }

    @Override
    public void reloadAmmo(Player p) {

    }

    @Override
    public ArrayList<Target> getPossibleTarget(Player player) {
        //TODO this card has all the cells in the map as targets!
        return null;
    }
}
