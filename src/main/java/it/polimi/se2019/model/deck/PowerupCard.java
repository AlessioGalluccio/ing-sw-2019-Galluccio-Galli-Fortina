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
     * Create and send a message containing the possible targets to the view of player
     * @param player the player who wants to use this card
     * @param playerView receiver of the message
     * @return the possible target that player can hit. It return null if there is no target
     */
    public abstract ArrayList<Target> sendPossibleTarget(Player player, PlayerView playerView);

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

    @Override
    public int getID() {
        return ID;
    }

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

    @Override
    public abstract void useCard(Player author);
}
