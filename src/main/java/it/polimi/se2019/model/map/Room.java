package it.polimi.se2019.model.map;

public class Room {
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
        return cellRoom.clone();
    }

    /**
     *
     * @return list of players on this cell
     */
    public ArrayList<Player> getPlayerHere() {

    }


}
