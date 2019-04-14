package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

public class AmmoOnlyCard implements AmmoConvertibleCard {
    private AmmoDeck deck;
    private ColorRYB colorSingle;
    private ColorRYB colorDouble;

    public AmmoOnlyCard(ColorRYB colorSingle, ColorRYB colorDouble) {
        this.colorSingle = colorSingle;
        this.colorDouble = colorDouble;
    }


    @Override
    public void reloadAmmo(Player p) {

    }

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException{
        if(this.deck==null) this.deck = (AmmoDeck) deck;
        else throw new AlreadyDeckException("This card " + this +" has already a deck!");
    }

    @Override
    public void discard() {
        deck.getInUseCard().remove(this);
        deck.getUsedCard().add(this);
    }

    @Override
    public void useCard() {

    }

    public AmmoOnlyCard() {

    }


}
