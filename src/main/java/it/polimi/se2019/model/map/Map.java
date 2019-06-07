package it.polimi.se2019.model.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.view.remoteView.MapView;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public abstract class Map implements Serializable {
    private int ID;
    private final Cell[][] cell;    //Cell[X][Y], according to cartesian plane (0,0 is at bottom-left)
    private transient final ArrayList<Room> room;
    private final String description;

    Map(InitializeMap init, String description, int ID) {
        this.cell = init.cell;
        this.room = init.room;
        this.description = description;
        this.ID = ID;
    }

    /**
     * Create a new map with the same cell, it's not a deep copy
     * @return A copy of the map
     */
    public Cell[][] getCell(){
        //Create a new matrix with the SAME cell, not deep copy
        Cell[][] cellToReturn = new Cell[cell.length][cell[1].length];
        for(int i=0; i<cell.length; i++){
            for(int j=0; j<cell[i].length; j++){
                cellToReturn[i][j] = cell[i][j];
            }
        }
        return cellToReturn;
    }

    /**
     * Return a shallow copy of the rooms
     * @return copy of the rooms
     */
    public List<Room> getRooms() {
        return new ArrayList<Room>(room);
    }

    /**
     * Return the description of this map
     * @return description fo this map
     */
    public String getDescription() {
        return description;
    }

    public int getID() {
        return ID;
    }

    /**
     * Compute distance from start to end
     * @param cellStart cell to start the distance
     * @param cellEnd cell to end the distance
     * @return distance
     */
    public int getDistance(Cell cellStart, Cell cellEnd) {
        if(cellEnd == cellStart) return 0;
        int distance;

        //It's not very optimized
        for(distance=0; !getCellAtDistance(cellStart, distance).contains(cellEnd); distance++);
        return distance;

        /*if(cellEnd == cellStart) return 0;
        if(cellStart.getNorthBorder().isCrossable()) {
            return getDistance(cell[cellStart.getCoordinateX()][cellStart.getCoordinateY()+1], cellEnd) +1;
        }
        if(cellStart.getEastBorder().isCrossable()) return getDistance(cell[cellStart.getCoordinateX()+1][cellStart.getCoordinateY()], cellEnd) +1;
        if(cellStart.getSouthBorder().isCrossable()) return getDistance(cell[cellStart.getCoordinateX()][cellStart.getCoordinateY()-1], cellEnd) +1;
        if(cellStart.getWestBorder().isCrossable()) return getDistance(cell[cellStart.getCoordinateX()-1][cellStart.getCoordinateY()], cellEnd) +1;
        return 0;*/
    }

    /**
     * Return the cell with coordinate x,y
     * @param x Coordinate x of the cell
     * @param y Coordinate y of the cell
     * @return The cell with coordinate x,y , null if there's not
     */
    public Cell getCellByCoo(int x, int y) {
        if(x<cell.length&&y<cell[x].length&&cell[x][y]!=null) return cell[x][y];
        return null;
    }

    /**
     * Return all cells up to distance indicated by range
     * @param cellStart Cell from which start
     * @param range the maximum distance
     * @return list of cell in the range
     * @throws IllegalArgumentException Range must be >= 0
     */
    public List<Cell> getCellAtDistance(Cell cellStart, int range) throws IllegalArgumentException {
        if(range < 0) throw new IllegalArgumentException();

        //Using set we avoid duplicates
        Set<Cell> set = new HashSet<>();
        if(range != 0) {
            if(cellStart.getNorthBorder().isCrossable()) set.addAll(getCellAtDistance(cell[cellStart.getCoordinateX()][cellStart.getCoordinateY() + 1], range-1));
            if(cellStart.getEastBorder().isCrossable()) set.addAll(getCellAtDistance(cell[cellStart.getCoordinateX() + 1][cellStart.getCoordinateY()], range-1));
            if(cellStart.getSouthBorder().isCrossable()) set.addAll(getCellAtDistance(cell[cellStart.getCoordinateX()][cellStart.getCoordinateY() - 1], range-1));
            if(cellStart.getWestBorder().isCrossable()) set.addAll(getCellAtDistance(cell[cellStart.getCoordinateX() - 1][cellStart.getCoordinateY()], range-1));
        }

        set.add(cellStart);
        return new ArrayList<>(set);
    }

    /**
     * Return all cells in the direction indicated
     * @param cellStart Cell from which start
     * @param NESW The direction (N - north, E - East, S - South, W - West)
     * @return If direction is correct return a list of cell in the direction
     * @throws IllegalArgumentException if direction doesn't exist
     */
    public List<Cell> getCellInDirection(Cell cellStart, char NESW) throws IllegalArgumentException{
        ArrayList<Cell> cells = new ArrayList<>();
        NESW = Character.toUpperCase(NESW);
        switch (NESW) {
            case 'N':
                //Fix the column (X) and scroll the row (Y) up to the top
                for(int i = cellStart.getCoordinateY(); i < cell[cellStart.getCoordinateY()].length; i++) {
                    if(cell[cellStart.getCoordinateX()][i] == null) break;
                    cells.add(cell[cellStart.getCoordinateX()][i]);
                }
                break;
            case 'E':
                //Fix the row (Y) and scroll the column (X) up to the right margin
                for(int i = cellStart.getCoordinateX(); i < cell.length; i++) {
                    if(cell[i][cellStart.getCoordinateY()] == null) break;
                    cells.add(cell[i][cellStart.getCoordinateY()]);
                }
                break;
            case 'S':
                //Fix the column (X) and scroll the row (Y) down to the bottom
                for(int i = cellStart.getCoordinateY(); i >= 0; i--) {
                    if(cell[cellStart.getCoordinateX()][i] == null) break;
                    cells.add(cell[cellStart.getCoordinateX()][i]);
                }
                break;
            case 'W':
                //Fix the row (Y) and scroll the column (X) up to the left margin
                for(int i = cellStart.getCoordinateX(); i >= 0; i--) {
                    if(cell[i][cellStart.getCoordinateY()] == null) break;
                    cells.add(cell[i][cellStart.getCoordinateY()]);
                }
                break;
                default: throw new IllegalArgumentException("You can only choose 4 direction: N - north, E - East, S - South, W - West");
        }
        return cells;
    }

    /**
     * If the card on a cell was picked, put a new card on the cell
     */
    public void reloadAllCell() {
        for(Cell[] arrayCell : cell) { //Scan all the row (X)
            for(Cell c : arrayCell) { //Scan all the column (Y)
                if(c != null) c.reloadCard();
            }
        }
    }

    public Map clone() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AmmoCard.class, new JsonAdapter<AmmoCard>())
                .registerTypeAdapter(Border.class, new JsonAdapter<Border>())
                .registerTypeAdapter(Map.class, new JsonAdapter<Map>())
                .registerTypeAdapter(Cell.class, new JsonAdapter<Cell>())
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>())
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        Type TYPE = new TypeToken<Map>() {
        }.getType();

        return gson.fromJson(gson.toJson(this, TYPE), TYPE);
    }

    public void attach(MapView mapView) {
        for(Cell[] c1 : cell) {
            for(Cell c : c1) {
                if(c!=null) c.attach(mapView);
            }
        }
    }
}

class InitializeMap {
    Cell[][] cell;
    ArrayList<Room> room = new ArrayList<>();

    InitializeMap(int numOfRow, int numOfColumn) {
        cell = new Cell[numOfColumn][numOfRow];
    }

    /**
     * Blue room of Map 1 and Map 2 has the same coordinate
     * This method initialize the common room
     */
    void addCommonBlueRoom() {
        ArrayList<Cell> cellForRoom = new ArrayList<>();
        cellForRoom.add(cell[0][2]);
        cellForRoom.add(cell[1][2]);
        cellForRoom.add(cell[2][2]);

        room.add(new Room((CellSpawn) cell[2][2], "BLUE", new ArrayList<>(cellForRoom)));
    }

    /**
     * Map 4 and map 2 have the same yellow and green room
     * This method initialize the common room
     * @param weaponDeck
     * @param ammoDeck
     */
    void createCommonYellowGreenRoom(WeaponDeck weaponDeck, AmmoDeck ammoDeck) {
        cell[2][0] = new CellAmmo(new Passage(), new Passage(), new Wall(), new Door(), 2, 0, ammoDeck);
        cell[3][0] = new CellSpawn(new Passage(), new Wall(), new Wall(), new Passage(), 3, 0, weaponDeck);
        cell[3][1] = new CellAmmo(new Door(), new Wall(), new Passage(), new Passage(), 3, 1, ammoDeck);
        cell[2][1] = new CellAmmo(new Door(), new Passage(), new Passage(), new Wall(), 2, 1, ammoDeck);

        ArrayList<Cell> cellForRoom = new ArrayList<>();
        cellForRoom.add(cell[2][0]);
        cellForRoom.add(cell[3][0]);
        cellForRoom.add(cell[3][1]);
        cellForRoom.add(cell[2][1]);

        room.add(new Room((CellSpawn) cell[3][0], "YELLOW", new ArrayList<>(cellForRoom)));

        cell[3][2] = new CellAmmo(new Wall(), new Wall(), new Door(), new Door(), 3, 2, ammoDeck);

        cellForRoom.clear();
        cellForRoom.add(cell[3][2]);

        room.add(new Room(null, "GREEN", new ArrayList<>(cellForRoom)));
    }

    /**
     * Map 4 and map 3 have the same red room
     * This method initialize the common room
     * @param weaponDeck
     * @param ammoDeck
     */
    void createCommonRedRoom(WeaponDeck weaponDeck, AmmoDeck ammoDeck) {
        cell[0][1] = new CellSpawn(new Passage(), new Wall(), new Door(), new Wall(), 0, 1, weaponDeck);
        cell[0][2] = new CellAmmo(new Wall(), new Door(), new Passage(), new Wall(), 0, 2, ammoDeck);

        ArrayList<Cell> cellForRoom = new ArrayList<>();
        cellForRoom.add(cell[0][1]);
        cellForRoom.add(cell[0][2]);

        room.add(new Room((CellSpawn) cell[0][1], "RED", new ArrayList<>(cellForRoom)));

        //This line is also in common
        cell[1][2] = new CellAmmo(new Wall(), new Passage(), new Door(), new Door(), 1, 2, ammoDeck);

    }
}

