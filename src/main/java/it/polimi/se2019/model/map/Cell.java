package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.Card;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;


public abstract class Cell {

    private Border northBorder;
    private Border eastBorder;
    private Border southBorder;
    private Border westBorder;
    private ArrayList<Player> playerHere;
    private Room room;
    //private int ID;
    private int coordinateX;
    private int coordinateY;

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

    }

    /**
     *
     * @return the card on the cell
     */
    public Card grabCard() {    //abstract

    }

    public void reloadCard() {  //abstract

    }

}
