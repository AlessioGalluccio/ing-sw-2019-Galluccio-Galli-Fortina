package it.polimi.se2019.model.deck;

public abstract class PowerupCard implements AmmoCard {

    public PowerupCard(Color color) {
        this.color = color;
    }

    private Color color;

}
