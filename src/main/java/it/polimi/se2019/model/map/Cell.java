package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;
import java.util.List;

public abstract class Cell implements Target {

    private Border northBorder;
    private Border eastBorder;
    private Border southBorder;
    private Border westBorder;
    private Room room;
    private Coordinate coordinate;
    private ArrayList<Player> playerHere = new ArrayList<>();

    protected Cell(Border north, Border east, Border south, Border west, int x, int y) {
        this.northBorder = north;
        this.eastBorder = east;
        this.southBorder = south;
        this.westBorder = west;
        this.coordinate = new Coordinate(x,y);
    }

    public int getCoordinateX() { return coordinate.getX(); }

    public int getCoordinateY() {
        return coordinate.getY();
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
        return GameHandler.getCellByCoordinate(coordinate.getX() , coordinate.getY() + 1);
    }

    public Cell getEastCell(){
        return GameHandler.getCellByCoordinate(coordinate.getX() + 1 , coordinate.getY());
    }

    public Cell getSouthCell(){
        return GameHandler.getCellByCoordinate(coordinate.getX(), coordinate.getY() - 1);
    }

    public Cell getWestCell(){
        return GameHandler.getCellByCoordinate(coordinate.getX() - 1, coordinate.getY());
    }

    public Room getRoom() {
        return room;
    }

    /**
     *
     * @return list of players on this cell
     */
    public List<Player> getPlayerHere() {
        return new ArrayList<>(playerHere);
    }

    /**
     *
     * @param cardID
     * @return the card on the cell
     * @throws NotCardException
     */
    public abstract Card grabCard(int cardID) throws NotCardException;

    /**
     *
     * @return
     */
    public abstract List<Integer> getCardID();

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    protected abstract void reloadCard();

    protected void setRoom(Room room) {
        if(this.room==null) this.room = room;
        else throw new AlreadyRoomException("This cell " + this +" has already a room!");
    }

    /**
     * When a player is moved in this cell, he is added to a list
     * @param playerToAdd the new player in the cell to add
     * @throws NotHereException the player position is not this cell
     */
    public void addPlayer(Player playerToAdd) {
        //Add here the player ONLY IF its position is this cell
        if (playerToAdd.getCell().getCoordinateX() == coordinate.getX() &&
                playerToAdd.getCell().getCoordinateY() == coordinate.getY()) {
            playerHere.add(playerToAdd);
            room.addPlayer(playerToAdd);
        } else throw new NotHereException(playerToAdd.toString() + " is not here " +toString());
    }

    /**
     * When a player is moved from this cell, he is removed from the list
     * @param playerToRemove the player who was moved
     * @throws NotHereException the player was not moved from here
     */
    public void removePlayer(Player playerToRemove) {
        if (playerToRemove.getCell().getCoordinateX() == coordinate.getX() &&
                playerToRemove.getCell().getCoordinateY() == coordinate.getY()) {
            playerHere.remove(playerToRemove);
            room.removePlayer(playerToRemove);
        }
        else throw new NotHereException(playerToRemove.toString() + " was not here " +toString());
    }

    @Override
    public String toString() {
        return "Cell{" +
                "X=" + coordinate.getX() +
                ", Y=" + coordinate.getY() +
                '}';
    }
}
