package it.polimi.se2019.model.deck;


import java.io.Serializable;

/**
 * @author Galli
 */
public class PointCard implements Serializable {

    private static final long serialVersionUID = 2961486207387549526L;
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
