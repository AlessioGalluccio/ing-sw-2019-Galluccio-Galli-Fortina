package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoConvertibleCard;
import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.Card;

public class CellAmmo extends Cell {
    private AmmoConvertibleCard ammo;

    protected CellAmmo(Border north, Border east, Border south, Border west, int x, int y, AmmoDeck deck) {
        super(north, east, south, west, x, y, deck);
    }

    /**
     *
     * @return ammmo card that can be grab here
     */
    public AmmoConvertibleCard getAmmo(){
        return null; //TODO implementare
    }

    @Override
    public Card grabCard() {

        return null; //TODO implementare
    }

    @Override
    public void reloadCard() {

    }
}
