package it.polimi.se2019.model.map;

import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ModelViewMess.CellModelMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Cell extends Observable implements Target, Serializable {

    private Border northBorder;
    private Border eastBorder;
    private Border southBorder;
    private Border westBorder;
    private transient Room room;
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

    public Room getRoom() {
        return room;
    }

    /**
     * Return a shallow copy of the player on this cell
     * @return list of players on this cell
     */
    public List<Player> getPlayerHere() {
        return new ArrayList<>(playerHere);
    }

    /**
     * Each cell has some card on it, this method grab a card
     * @param cardID ID of the card I wanna grab (ONLY FOR THOSE CELL THAT HAS MORE CARD ON IT) --Not CellAmmo
     * @return the card on the cell
     * @throws NotCardException If card has already taken or there not a card with the ID
     */
    public abstract Card grabCard(int cardID) throws NotCardException;

    /**
     * Each cell has some card on it, this method return a list of that cards' IDs
     * @return the IDs of the cards on this cell
     */
    public abstract List<Integer> getCardID();

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    protected abstract void reloadCard();

    /**
     * Each cell has to have a room, this method set the appropriate room
     * @param room room of this cell
     * @throws AlreadyRoomException if a room was already set for this cell, it can't be modified!
     */
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
            notifyObservers(new CellModelMessage(this.clone()));
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
            notifyObservers(new CellModelMessage(this.clone()));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return northBorder.getClass() == cell.northBorder.getClass() &&
                eastBorder.getClass() == cell.eastBorder.getClass()&&
                southBorder.getClass() == cell.southBorder.getClass() &&
                westBorder.getClass()== cell.westBorder.getClass()&&
                //Objects.equals(room, cell.room) &&
                coordinate.equals(cell.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(northBorder, eastBorder, southBorder, westBorder, coordinate);
    }

    public abstract Cell clone();
}
