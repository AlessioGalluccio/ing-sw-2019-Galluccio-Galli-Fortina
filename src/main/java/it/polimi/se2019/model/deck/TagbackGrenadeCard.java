package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.ui.ConsoleColor;


/**
 * @author Galli
 */
public class TagbackGrenadeCard extends PowerupCard{
    private static final long serialVersionUID = -7421528938667576629L;

    public TagbackGrenadeCard(ColorRYB color, int id, int IDtype) {
        super(color, id, IDtype);
    }

    /**
     * String the full card, representing each attributes the name of the card (Tagback Grenade) and color
     * Works with ANSI code
     * @return The representation of the card
     */
    @Override
    public String toString() {
        return ConsoleColor.colorByColor(getAmmo().toString()) + "Tagback Grenade" + ConsoleColor.RESET;
    }
}