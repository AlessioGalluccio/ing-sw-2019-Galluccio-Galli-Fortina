package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;

public class AmmoPowerupCard implements AmmoConvertibleCard {

    private ColorRYB color1;
    private ColorRYB color2;

    public AmmoPowerupCard(ColorRYB color1, ColorRYB color2) {
        this.color1 = color1;
        this.color2= color2;
    }

    @Override
    /**
     *
     */
    public void reloadAmmo(Player p) {

    }

    @Override
    public void useCard() {

    }

    private void pickPowerup(){

    }
}
