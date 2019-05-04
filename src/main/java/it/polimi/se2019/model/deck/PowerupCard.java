package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.PlayerView;

import java.util.ArrayList;

import java.util.List;

public abstract class PowerupCard implements Card {
    private transient PowerupDeck deck;
    private final ColorRYB color;
    //Due to his immutable nature, ID is not necessary

    public PowerupCard(ColorRYB color) {
        this.color = color;
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
     * Set his deck for the card
     * @param deck Deck of relatives cards to set
     * @throws AlreadyDeckException If you try to reset the deck, it can change during game
     */
    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
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

    @Override
    public String toString() {
        return "PowerupCard{" +
                "color=" + color +
                '}';
    }
}
