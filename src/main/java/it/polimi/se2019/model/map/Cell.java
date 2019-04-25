package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;


public abstract class Cell implements Target {

    private Border northBorder;
    private Border eastBorder;
    private Border southBorder;
    private Border westBorder;
    private Room room;
    private int coordinateX;
    private int coordinateY;

    private ArrayList<Player> playerHere;
    private Deck deck;

    protected Cell(Border north, Border east, Border south, Border west, int x, int y, Deck deck) {
        this.northBorder = north;
        this.eastBorder = east;
        this.southBorder = south;
        this.westBorder = west;
        this.coordinateX = x;
        this.coordinateY = y;
        this.deck = deck;
    }

    public int getCoordinateX() { return coordinateX; }

    public int getCoordinateY() {
        return coordinateY;
    }

    public Border getNorthBorder() {
        return northBorder;
    }

    public Border getEastBorder() {
        return eastBorder;
    }

    public Border getSouthBorder() {
        return southBorder;
    }

    public Border getWestBorder() {
        return westBorder;
    }

    public Room getRoom() {
        return room;
    }

    /**
     *
     * @return list of players on this cell
     */
    public ArrayList<Player> getPlayerHere() {

        return null;    //TODO implementare
    }

    /**
     *
     * @return the card on the cell
     */
    public abstract Card grabCard();

    public abstract void reloadCard();

    public void setRoom(Room room) {
        if(this.room==null) this.room = room;
        else throw new AlreadyRoomException("This cell " + this +" has already a room!");
    }
}
