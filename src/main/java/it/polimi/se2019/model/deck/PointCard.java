package it.polimi.se2019.model.deck;

public class PointCard implements Card{

    private int value;

    public PointCard(int value) {
        this.value = value;
    }

    @Override
    public void useCard() {

    }

    public int getValue(){

        return value;
    }

}
