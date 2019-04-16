package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import java.util.List;

public interface AmmoConvertibleCard extends Card {
    List<ColorRYB> getAmmo();
    void reloadAmmo(Player p);
}
