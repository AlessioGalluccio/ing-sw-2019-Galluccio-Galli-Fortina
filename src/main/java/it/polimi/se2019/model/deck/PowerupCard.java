package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

public abstract class PowerupCard implements AmmoConvertibleCard {
    private ColorRYB color;

    public PowerupCard(ColorRYB color) {
        this.color = color;
    }

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {

    }

    @Override
    public void discard() {

    }

    @Override
    public void useCard() {

    }

    @Override
    public void reloadAmmo(Player p) {

    }
}
