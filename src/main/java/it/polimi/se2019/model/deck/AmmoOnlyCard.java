package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.*;

import java.util.*;

import static it.polimi.se2019.model.player.ColorRYB.*;


public class AmmoOnlyCard implements AmmoCard {
    private final int ID;
    private transient AmmoDeck deck;
    private final ColorRYB colorSingle;
    private final ColorRYB colorDouble;

    public AmmoOnlyCard(ColorRYB colorSingle, ColorRYB colorDouble, int ID) {
        this.colorSingle = colorSingle;
        this.colorDouble = colorDouble;
        this.ID = ID;
    }

    /**
     *
     * @return List of ammo included in the card
     */
    @Override
    public List<ColorRYB> getAmmo() {
        ArrayList<ColorRYB> ammo = new ArrayList<>();
        ammo.add(colorSingle);
        ammo.add(colorDouble);
        ammo.add(colorDouble);
        return ammo;
    }

    @Override
    public int getID() {
        return ID;
    }


    /**
     * Reload a player's ammo using this card
     * @param p player to relaod
     */
    @Override
    public void reloadAmmo(Player p) throws TooManyException {
        AmmoBag ammoPlayer = p.getAmmo();
        List<ColorRYB> ammoList = getAmmo();
        p.setAmmoBag(ammoPlayer.getRedAmmo() + Collections.frequency(ammoList, RED),
                ammoPlayer.getYellowAmmo() + Collections.frequency(ammoList, YELLOW),
                ammoPlayer.getBlueAmmo() + Collections.frequency(ammoList, BLUE));
    }

    /**
     * Set his deck for the card
     * @param deck Deck of relatives cards to set
     * @throws AlreadyDeckException If you try to reset the deck, it can change during game
     */
    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException{
        if(this.deck==null) this.deck = (AmmoDeck) deck;
        else throw new AlreadyDeckException("This card " + this +" has already a deck!");
    }

    /**
     * Discard a card
     */
    @Override
    public void discard() {
        deck.discard(this);
    }

    /**
     * Add to player AmmoBag the new ammos
     * @param author Who use the card
     */
    @Override
    public void useCard(Player author) throws TooManyException {
        reloadAmmo(author);
    }

    @Override
    public String toString() {
        return "AmmoOnlyCard{" +
                "colorSingle=" + colorSingle +
                ", colorDouble=" + colorDouble +
                '}';
    }
}
