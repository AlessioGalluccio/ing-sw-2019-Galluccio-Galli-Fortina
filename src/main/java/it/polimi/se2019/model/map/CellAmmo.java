package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoConvertibleCard;
import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.Card;
import it.polimi.se2019.model.deck.WeaponDeck;

public class CellAmmo extends Cell {
    private AmmoConvertibleCard ammo;
    private transient AmmoDeck deck;

    public CellAmmo(Border north, Border east, Border south, Border west, int x, int y, AmmoDeck deck) {
        super(north, east, south, west, x, y);
        this.deck = deck;
    }


    @Override
    public Card grabCard() {
        /*Card cardToReturn = ammo;
        ammo = null;
        return cardToReturn;*/
         return null; //TODO decidere come implemenatare!
    }

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    @Override
    protected void reloadCard() {
        if(ammo==null) ammo = deck.pick();
    }
}
