package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

import java.util.List;
import java.util.ArrayList;

public class AmmoOnlyCard implements AmmoConvertibleCard {
    private transient AmmoDeck deck;
    private ColorRYB colorSingle;
    private ColorRYB colorDouble;

    public AmmoOnlyCard(ColorRYB colorSingle, ColorRYB colorDouble) {
        this.colorSingle = colorSingle;
        this.colorDouble = colorDouble;
    }

    @Override
    public List<ColorRYB> getAmmo() {
        ArrayList<ColorRYB> ammo = new ArrayList<>();
        ammo.add(colorSingle);
        ammo.add(colorDouble);
        ammo.add(colorDouble);
        return ammo;
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
        deck.discard(this);
    }

    @Override
    public void useCard() {

    }

    public AmmoOnlyCard() {

    }


}
