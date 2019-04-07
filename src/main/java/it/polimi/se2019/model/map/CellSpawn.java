package it.polimi.se2019.model.map;

import java.util.ArrayList;

import it.polimi.se2019.model.deck.Card;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.player.Color;

public class CellSpawn extends Cell {
    private ArrayList<WeaponCard> weapon;
    private Color color;
    private Room room;

    /**
     *
     * @return list of WeaponCard that can be pick here
     */
    public ArrayList<WeaponCard> getWeapon() {

        return null; //TODO implementare
    }

    public Color getColor() {
        return color;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public Card grabCard(){
        return null; //TODO implementare
    }

    @Override
    public void reloadCard(){

    }

}
