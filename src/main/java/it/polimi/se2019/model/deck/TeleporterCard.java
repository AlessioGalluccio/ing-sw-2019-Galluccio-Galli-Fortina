package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.ui.ConsoleColor;

/**
 * @author Galli
 */
public class TeleporterCard extends PowerupCard{
    private static final long serialVersionUID = -8724215878484689333L;

    public TeleporterCard(ColorRYB color, int id, int IDtype) {
        super(color, id, IDtype);
    }

    /**
     * String the full card, representing each attributes the name of the card (Teleporter) and color
     * Works with ANSI code
     * @return The representation of the card
     */
    @Override
    public String toString() {
        return ConsoleColor.colorByColor(getAmmo().toString()) + "Teleporter" + ConsoleColor.RESET;
    }
}
