package it.polimi.se2019.model.deck;


import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.ui.ConsoleColor;

public class NewtonCard extends PowerupCard{
    private static final long serialVersionUID = -8414803787414827397L;

    public NewtonCard(ColorRYB color, int id, int IDtype) {
        super(color, id, IDtype);
    }

    /**
     * String the full card, representing each attributes the name of the card (Newton) and color
     * Works with ANSI code
     * @return The representation of the card
     */
    @Override
    public String toString() {
        return ConsoleColor.colorByColor(getAmmo().toString()) + "Newton" + ConsoleColor.RESET;
    }
}