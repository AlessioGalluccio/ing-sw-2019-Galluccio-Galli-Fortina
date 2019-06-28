package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Room implements Target, Serializable {
    private static final long serialVersionUID = -1001657778100220436L;
    private transient CellSpawn spawnCell;
    private String color;
    private transient ArrayList<Cell> cellRoom;
    private transient ArrayList<Player> playerHere = new ArrayList<>();

    public Room(CellSpawn spawnCell, String color, ArrayList<Cell> cellRoom) {
        this.spawnCell = spawnCell;
        this.color = color;
        this.cellRoom = cellRoom;

        for(Cell c : cellRoom) {
            c.setRoom(this);
        }
    }

    public Cell getSpawnCell() {
        return spawnCell;
    }

    public String getColor() {
        return color;
    }

    /**
     *
     * @return list of cell in this room
     */
    public List<Cell> getCellRoom() {

        return new ArrayList<>(cellRoom);
    }

    /**
     *
     * @return list of players on this cell
     */
    public ArrayList<Player> getPlayerHere() {

        return new ArrayList<>(playerHere);
    }

    /**
     * Compare two room base on the unique color
     * @param o the second room to compare
     * @return true if the rooms have the same color, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return color.equals(room.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spawnCell.getCoordinateX(), spawnCell.getCoordinateY(), color);
    }

    /**
     * When a player is moved in this room, he is added to a list
     * @param playerToAdd the new player in the room to add
     * @throws NotHereException the player position is not in this room
     */
    void addPlayer(Player playerToAdd) {
        if(playerToAdd.getCell().getRoom().getColor().equals(color)) playerHere.add(playerToAdd);
        else throw new NotHereException(playerToAdd.toString() + " is not in this room, color: " + color);
    }

    /**
     * When a player is moved from this room, he is removed from the list
     * @param playerToRemove the player who was moved
     * @throws NotHereException the player was not moved from here
     */
    void removePlayer(Player playerToRemove) {
        if(playerToRemove.getCell().getRoom().getColor().equals(color))  playerHere.remove(playerToRemove);
        else throw new NotHereException(playerToRemove.toString() + " was not in this room, color: " + color);
    }
}
