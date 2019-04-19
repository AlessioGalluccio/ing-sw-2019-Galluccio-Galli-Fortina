package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;

import java.util.List;

public interface AmmoConvertibleCard extends Card {

    /**
     *
     * @return List of ammo included in the card
     */
    List<ColorRYB> getAmmo();
    void reloadAmmo(Player p) throws TooManyException;
}
