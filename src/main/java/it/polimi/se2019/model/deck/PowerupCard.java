package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.Player;

public abstract class PowerupCard implements AmmoConvertibleCard {

    private Color color;

    public PowerupCard() {}

    @Override
    public void useCard() {

    }

    @Override
    public void reloadAmmo(Player p) {
        
    }
}
