package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

import java.util.List;

public abstract class PowerupCard implements AmmoConvertibleCard {
    private transient PowerupDeck deck;
    private ColorRYB color;

    public PowerupCard(ColorRYB color) {
        this.color = color;
    }

    @Override
    public List<ColorRYB> getAmmo() {
        return null; //TODO
    }

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
        if(this.deck==null) this.deck = (PowerupDeck) deck;
        else throw new AlreadyDeckException("This card " + this +" has already a deck!");
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
