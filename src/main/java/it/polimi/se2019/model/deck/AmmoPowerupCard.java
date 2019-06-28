package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static it.polimi.se2019.model.player.ColorRYB.*;


public class AmmoPowerupCard implements AmmoCard {
    private static final long serialVersionUID = -65131800141018976L;
    private final int ID;
    private final int IDtype;
    private transient AmmoDeck deck;
    private final transient PowerupDeck powerupDeck;
    private final ColorRYB color1;
    private final ColorRYB color2;

    static final String TOO_MANY_AMMO = "You have already max ammo. ";

    AmmoPowerupCard(ColorRYB color1, ColorRYB color2, PowerupDeck powerupDeck, int ID, int IDtype) {
        this.color1 = color1;
        this.color2 = color2;
        this.powerupDeck = powerupDeck;
        this.ID = ID;
        this.IDtype = IDtype;
    }

    /**
     * Set his deck for the card
     * @param deck Deck of relatives cards to set
     * @throws AlreadyDeckException If you try to reset the deck, it can change during game
     */
    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
        if(this.deck==null) this.deck = (AmmoDeck) deck;
        else throw new AlreadyDeckException("This card " + this + " has already a deck!");
    }

    /**
     *
     * @return List of ammo included in the card
     */
    @Override
    public List<ColorRYB> getAmmo() {
        ArrayList<ColorRYB> ammo = new ArrayList<>();
        ammo.add(color1);
        ammo.add(color2);
        return ammo;
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
     * The ID Type isn't unique for each card.
     * It is the same for the card with the same status, so for the card which has the same color of ammo.
     * @return the id type of the card
     */
    @Override
    public int getIDtype() {
        return IDtype;
    }

    /**
     * Reload a player's ammo using this card
     * @param p player to reload
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
     * Discard a card
     */
    @Override
    public void discard() {
        deck.discard(this);
    }

    /**
     * Add to player's AmmoBag the new ammos and pick a powerup card
     * @param author Who use the card
     * @throws TooManyException if the player has already three powerup (the maximum)
     */
    @Override
    public void useCard(Player author) throws TooManyException {
        //in this way, reloadAmmo and addPowerup are executed even if one of the two
        //launches an exception
        try{
            reloadAmmo(author);
        }catch(TooManyException e){
            author.addPowerupCard(powerupDeck.pick());
            throw new TooManyException(TOO_MANY_AMMO);
        }
        author.addPowerupCard(powerupDeck.pick());
    }

    @Override
    public String toString() {
        return "AmmoPowerupCard{" +
                "color1=" + color1 +
                ", color2=" + color2 +
                " IDimg= " +IDtype +
                '}';
    }
}
