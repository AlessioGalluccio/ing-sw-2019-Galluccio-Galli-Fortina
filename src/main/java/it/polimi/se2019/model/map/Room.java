package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;


public class Room implements Target {
    private final CellSpawn spawnCell;
    private final String color;
    private final ArrayList<Cell> cellRoom;
    private ArrayList<Player> playerHere;

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

    }

    /**
     *
     * @return list of players on this cell
     */
    public ArrayList<Player> getPlayerHere() {

    }


}
