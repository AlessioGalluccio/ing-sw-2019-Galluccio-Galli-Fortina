package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;


public class Room implements Target {
    private CellSpawn spawnCell;
    private String color;
    private ArrayList<Cell> cellRoom;
    private ArrayList<Player> playerHere = new ArrayList<>();

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
    public ArrayList<Cell> getCellRoom() {

        return null; //TODO implementare
    }

    /**
     *
     * @return list of players on this cell
     */
    public ArrayList<Player> getPlayerHere() {

        return playerHere; //TODO implementare
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    public boolean equals(Room room){
        return (room.color.equals(this.color));
    }

    /**
     * When a player is moved in this room, he is added to a list
     * @param playerToAdd the new player in the room to add
     */
    protected void addPlayer(Player playerToAdd) {
        playerHere.add(playerToAdd);
    }

    /**
     * When a player is moved from this room, he is removed from the list
     * @param playerToRemove the player who was moved
     */
    protected void removePlayer(Player playerToRemove) {
        playerHere.remove(playerToRemove);
    }
}
