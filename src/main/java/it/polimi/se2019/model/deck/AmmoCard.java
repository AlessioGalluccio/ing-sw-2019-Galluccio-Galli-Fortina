package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;

import java.util.List;

public interface AmmoCard extends Card {

    /**
     *
     * @return List of ammo included in the card
     */
    List<ColorRYB> getAmmo();

    /**
     * Reload the player's ammo according to the card who implements this interface
     * @param p The player to reload
     * @throws TooManyException If the player has more then 3 ammo of any color
     */
    void reloadAmmo(Player p) throws TooManyException;

    /**
     * Apply the effect of the card to the player.
     * In this case, reload the ammo
     * @param author The palayer who use the card
     * @throws TooManyException If the player has more then 3 ammo of any color
     */
    void useCard(Player author) throws TooManyException;
}
