package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public abstract class PowerupCard implements Card {
    private static final long serialVersionUID = -678444631696460338L;
    private final int ID;
    private final int IDtype;
    private transient PowerupDeck deck;
    private final ColorRYB color;

    public PowerupCard(ColorRYB color, int ID, int IDtype) {
        this.color = color;
        this.ID = ID;
        this.IDtype = IDtype;
    }

    /**
     *
     * @return Ammo that can be change for this powerup
     */
    public ColorRYB getAmmo() {
        return color;
    }

    /**
     * return the color of this card, for the spawn
     * @return ColoRYB of the card
     */
    public String getColor(){
        switch(this.color){
            case RED: return "RED";
            case YELLOW: return "YELLOW";
            case BLUE: return "BLUE";
        }
        return null;
    }

    /**
     * The ID is an unique number for each card.
     * Can't exist two card of the SAME DECK with the same ID. If there are different there may be the same ID.
     * @return The unique ID of the card.
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * The ID Type isn't unique for each card.
     * It is the same for the card with the same status.
     * So for the powerup which has the same color and is the same type (Teleporter, Newtown, Tagback or Targetting).
     * @return the id type of the card
     */
    @Override
    public int getIDtype() {
        return IDtype;
    }

    /**
     * Set his deck for the card
     * @param deck Deck of relatives cards to set
     * @throws AlreadyDeckException If you try to reset the deck, it can change during game
     */
    @Override
    public void setDeck(Deck deck) {
        if(this.deck==null) this.deck = (PowerupDeck) deck;
        else throw new AlreadyDeckException("This card " + this +" has already a deck!");
    }

    /**
     * Discard a card
     */
    @Override
    public void discard() {
        deck.discard(this);
    }
}
