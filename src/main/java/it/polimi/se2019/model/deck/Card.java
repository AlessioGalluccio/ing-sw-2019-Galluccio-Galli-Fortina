package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Player;

public interface Card {
   void setDeck(Deck deck) throws AlreadyDeckException;
   void discard();
   void useCard(Player author);
}
