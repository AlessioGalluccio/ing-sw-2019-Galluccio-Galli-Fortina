package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
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

    private ArrayList<Player> playerHere = new ArrayList<>();
    private transient Deck deck;

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

    public Cell getNorthCell(){
        return GameHandler.getCellByCoordinate(coordinateX , coordinateY + 1);
    }

    public Cell getEastCell(){
        return GameHandler.getCellByCoordinate(coordinateX + 1 , coordinateY);
    }

    public Cell getSouthCell(){
        return GameHandler.getCellByCoordinate(coordinateX , coordinateY - 1);
    }

    public Cell getWestCell(){
        return GameHandler.getCellByCoordinate(coordinateX - 1, coordinateY);
    }

    public Room getRoom() {
        return room;
    }

    /**
     *
     * @return list of players on this cell
     */
    public ArrayList<Player> getPlayerHere() {

        return playerHere;    //TODO implementare
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

    /**
     * When a player is moved in this cell, he is added to a list
     * @param playerToAdd the new player in the cell to add
     */
    public void addPlayer(Player playerToAdd) {
        playerHere.add(playerToAdd);
        room.addPlayer(playerToAdd);
    }

    /**
     * When a player is moved from this cell, he is removed from the list
     * @param playerToRemove the player who was moved
     */
    public void removePlayer(Player playerToRemove) {
        playerHere.remove(playerToRemove);
        room.removePlayer(playerToRemove);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "X=" + coordinateX +
                ", Y=" + coordinateY +
                '}';
    }
}
