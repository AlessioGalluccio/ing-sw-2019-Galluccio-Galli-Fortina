package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.*;

import java.util.*;

import static it.polimi.se2019.model.player.ColorRYB.*;

/**
 * @author Galli
 */
public class AmmoOnlyCard implements AmmoCard {
    private static final long serialVersionUID = -2988304472701966764L;
    private final int ID;
    private final int IDtype;
    private transient AmmoDeck deck;
    private final ColorRYB colorSingle;
    private final ColorRYB colorDouble;

    AmmoOnlyCard(ColorRYB colorSingle, ColorRYB colorDouble, int ID,int IDtype) {
        this.colorSingle = colorSingle;
        this.colorDouble = colorDouble;
        this.ID = ID;
        this.IDtype = IDtype;
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

    /**
     * The ID Type isn't unique for each card.
     * It is the same for the card with the same status, so for the card which has the same color of ammo.
     * @return the id type of the card
     */
    @Override
    public int getIDtype() {
        return IDtype;
    }


    /**
     * The ID is an unique number for each card.
     * Can't exist two card of the SAME DECK with the same ID. If the decks are different there may be the same ID.
     * @return The unique ID of the card.
     */
    @Override
    public int getID() {
        return ID;
    }


    /**
     * Reload a player's ammo using this card
     * @param p player to reload
     */
    @Override
    public void reloadAmmo(Player p) throws TooManyException {
        List<ColorRYB> ammoList = getAmmo();
        AmmoBag ammoPlayer = p.getAmmo();
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
                " IDimg= " +IDtype +
                '}';
    }
}
