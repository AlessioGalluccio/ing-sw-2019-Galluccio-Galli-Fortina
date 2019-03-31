package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Player;

public interface AmmoConvertibleCard extends Card {



    public void reloadAmmo(Player p);
}
