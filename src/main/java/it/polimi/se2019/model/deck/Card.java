package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;

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

   int getID();

   int getIDtype();
}
