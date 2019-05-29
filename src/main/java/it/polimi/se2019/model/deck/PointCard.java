package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Player;

public class PointCard {

    private final int value;

    public PointCard(int value) {
        this.value = value;
    }

    /**
     *
     * @return value of point available in this card
     */
    public int getValue(){
        return value;
    }

}
