package it.polimi.se2019.model.map;

public interface Border {

    /**
     *
     * @return TRUE if a player can move through it, FALSE otherway
     */
    boolean isCrossable();
}
