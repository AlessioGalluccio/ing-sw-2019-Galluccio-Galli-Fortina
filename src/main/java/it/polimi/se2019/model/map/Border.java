package it.polimi.se2019.model.map;

import it.polimi.se2019.ui.PrintableDirection;

import java.io.Serializable;

/**
 * @author Galli
 */
public interface Border extends PrintableDirection, Serializable {

    /**
     *
     * @return TRUE if a player can move through it, FALSE otherwise
     */
    boolean isCrossable();
}
