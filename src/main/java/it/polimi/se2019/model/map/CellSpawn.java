package it.polimi.se2019.model.map;

import java.util.ArrayList;
import java.util.List;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.Card;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.deck.WeaponDeck;
import it.polimi.se2019.model.player.Color;

public class CellSpawn extends Cell {
    private ArrayList<WeaponCard> weapon;
    private Color color;

    protected CellSpawn(Border north, Border east, Border south, Border west, int x, int y, WeaponDeck deck) {
        super(north, east, south, west, x, y, deck);
    }

    /**
     *
     * @return list of WeaponCard that can be pick here
     */
    public List<WeaponCard> getWeapon() {

        return null; //TODO implementare
    }

    public Color getColor() {
        return color;
    }

    public Room getRoom() {
        return super.getRoom();
    }

    @Override
    public Card grabCard(){
        return null; //TODO implementare
    }

    @Override
    public void reloadCard(){

    }

}
