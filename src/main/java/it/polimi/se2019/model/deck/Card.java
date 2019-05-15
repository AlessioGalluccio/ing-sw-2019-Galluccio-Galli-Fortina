package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;

public interface Card {

   /**
    * Set his deck for the card
    * @param deck Deck of relatives cards to set
    * @throws AlreadyDeckException If you try to reset the deck, it can change during game
    */
   void setDeck(Deck deck) throws AlreadyDeckException;

   /**
    * Discard a card
    */
   void discard();

   /**
    * Apply the effect of the card to the author
    * @param author who use the card
    * @throws TooManyException if the effect of this card allow author to pick a new card, this exception is thrown whenever author has already three cards
    */
   void useCard(Player author) throws TooManyException;

   int getID();
}
