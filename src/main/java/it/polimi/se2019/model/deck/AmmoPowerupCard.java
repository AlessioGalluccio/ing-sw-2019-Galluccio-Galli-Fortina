package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

import java.util.List;
import java.util.ArrayList;


public class AmmoPowerupCard implements AmmoConvertibleCard {
    private transient AmmoDeck deck;
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
    public List<ColorRYB> getAmmo() {
        ArrayList<ColorRYB> ammo = new ArrayList<>();
        ammo.add(color1);
        ammo.add(color2);
        return ammo;
    }

    @Override
    public void reloadAmmo(Player p) {

    }

    /**
     * Discard a card
     */
    @Override
    public void discard() {
        deck.discard(this);
    }

    @Override
    public void useCard() {

    }

    private void pickPowerup(){

    }
}
