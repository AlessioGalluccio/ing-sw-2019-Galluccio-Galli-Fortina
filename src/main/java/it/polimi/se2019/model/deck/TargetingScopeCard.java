package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.ui.ConsoleColor;


/**
 * @author Galli
 */
public class TargetingScopeCard extends PowerupCard{
    private static final long serialVersionUID = 1400744600083145830L;

    public TargetingScopeCard(ColorRYB color, int id, int IDtype) {
        super(color, id, IDtype);
    }

    /**
     * String the full card, representing each attributes the name of the card (Targeting Scope) and color
     * Works with ANSI code
     * @return The representation of the card
     */
    @Override
    public String toString() {
        return ConsoleColor.colorByColor(getAmmo().toString()) + "Targeting Scope" + ConsoleColor.RESET;
    }
}