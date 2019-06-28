package it.polimi.se2019.model.deck;


import java.io.Serializable;

public interface Card extends Serializable {

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
    * The ID is an unique number for each card.
    * Can't exist two card of the SAME DECK with the same ID. If the decks are different there may be the same ID.
    * @return The unique ID of the card.
    */
   int getID();

   /**
    * The ID Type isn't unique for each card.
    * It is the same for the card with the same status.
    * @return the id type of the card
    */
   int getIDtype();
}
