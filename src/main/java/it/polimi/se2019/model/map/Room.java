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

        return null; //TODO implementare
    }


}
