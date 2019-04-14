package it.polimi.se2019.model.deck;

public interface Card {
   void setDeck(Deck deck) throws AlreadyDeckException;
   void discard();
   void useCard();
}
