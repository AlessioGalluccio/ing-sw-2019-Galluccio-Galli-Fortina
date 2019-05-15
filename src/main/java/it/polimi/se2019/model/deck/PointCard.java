package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Player;

public class PointCard implements Card{

    private final int value;

    public PointCard(int value) {
        this.value = value;
    }

    /**
     *
     * @param deck Deck of relatives cards to set
     * @throws UnsupportedOperationException Alaways thrown because pointCard dose not have a deck
     */
    @Override
    public void setDeck(Deck deck) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Point dose not have a deck!");
    }

    /**
     * DO NOTHING
     * @throws UnsupportedOperationException This card doesn't have a deck, can't be discarded
     */
    @Override
    public void discard() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Point cannot be discarded!");
    }

    @Override
    public void useCard(Player author) {

    }

    @Override
    public int getID() {
        return 0;
    }

    /**
     *
     * @return value of point available in this card
     */
    public int getValue(){
        return value;
    }

}
