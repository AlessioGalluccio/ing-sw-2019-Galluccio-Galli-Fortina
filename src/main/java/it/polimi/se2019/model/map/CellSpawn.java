package it.polimi.se2019.model.map;

import java.util.List;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.ColorRYB;

public class CellSpawn extends Cell {
    private final int MAX_WEAPONCARD = 3;
    private WeaponCard[] weapon = new WeaponCard[MAX_WEAPONCARD];
    private ColorRYB color;
    private transient WeaponDeck deck;

    protected CellSpawn(Border north, Border east, Border south, Border west, int x, int y, WeaponDeck deck) {
        super(north, east, south, west, x, y);
        this.deck = deck;
    }

    /**
     *
     * @return list of WeaponCard that can be pick here
     */
    public List<WeaponCard> getWeapon() {

        return null; //TODO implementare
    }

    public ColorRYB getColor() {
        return color;
    }

    public Room getRoom() {
        return super.getRoom();
    }

    @Override
    public Card grabCard(){
        return null; //TODO implementare
    }

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    @Override
    protected void reloadCard(){
        for(int i=0; i<MAX_WEAPONCARD; i++) {
            if(weapon[i]==null && deck.sizeUnususedCard()!=0) weapon[i]=deck.pick();
        }
    }

    @Override
    protected void setRoom(Room room) {
        super.setRoom(room);
        String colorRoom = getRoom().getColor().toUpperCase();
        switch (colorRoom) {
            case "BLUE":
                color = ColorRYB.BLUE; break;
            case "YELLOW":
                color = ColorRYB.YELLOW; break;
            case "RED":
                color = ColorRYB.RED; break;
            default:
                throw new IllegalArgumentException("Color room is not one of ColorRYB");
        }
    }

}
