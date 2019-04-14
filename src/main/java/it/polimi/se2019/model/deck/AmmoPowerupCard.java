package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

public class AmmoPowerupCard implements AmmoConvertibleCard {
    private AmmoDeck deck;
    private ColorRYB color1;
    private ColorRYB color2;

    public AmmoPowerupCard(ColorRYB color1, ColorRYB color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
        if(this.deck==null) this.deck = (AmmoDeck) deck;
        else throw new AlreadyDeckException("This card " + this + " has already a deck!");
    }

    @Override
    public void reloadAmmo(Player p) {

    }

    /**
     * Discard a card
     */
    @Override
    public void discard() {
        deck.getInUseCard().remove(this);
        deck.getUsedCard().add(this);
    }

    @Override
    public void useCard() {

    }

    private void pickPowerup(){

    }
}
