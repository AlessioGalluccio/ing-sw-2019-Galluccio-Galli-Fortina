package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.Player;

public class AmmoOnlyCard implements AmmoCard {

    private Color colorSingle;
    private Color colorDouble;

    @Override
    public void reloadAmmo(Player p) {

    }

    @Override
    public void useCard() {

    }

    public AmmoOnlyCard() {

    }


}
