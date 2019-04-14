package it.polimi.se2019.model.deck;

public class PointCard implements Card{

    private int value;

    public PointCard(int value) {
        this.value = value;
    }

    @Override
    public void setDeck(Deck deck) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Point dose not have a deck!");
    }

    @Override
    public void discard() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Point cannot be discarded!");
    }

    @Override
    public void useCard() {

    }

    public int getValue(){
        return value;
    }

}
