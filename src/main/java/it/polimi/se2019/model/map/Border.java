package it.polimi.se2019.model.map;

import java.io.Serializable;

public interface Border extends Serializable {

    /**
     *
     * @return TRUE if a player can move through it, FALSE otherway
     */
    boolean isCrossable();
}
