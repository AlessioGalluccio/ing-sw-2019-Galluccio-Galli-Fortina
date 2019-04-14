package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

public class AmmoOnlyCard implements AmmoConvertibleCard {

    private ColorRYB colorSingle;
    private ColorRYB colorDouble;

    public AmmoOnlyCard(ColorRYB colorSingle, ColorRYB colorDouble) {
        this.colorSingle = colorSingle;
        this.colorDouble = colorDouble;
    }

    @Override
    public void reloadAmmo(Player p) {

    }

    @Override
    public void useCard() {

    }

    public AmmoOnlyCard() {

    }


}
