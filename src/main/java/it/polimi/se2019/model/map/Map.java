package it.polimi.se2019.model.map;

import java.util.ArrayList;
import java.util.List;

public abstract class Map extends java.util.Observable {
    private final Cell[][] cell;    //Cell[X][Y], according to cartesian plane (0,0 bottom-left)
    private final ArrayList<Room> room;
    private final String description;

    Map(InitializeMap init, String description) {
        this.cell = init.cell;
        this.room = init.room;
        this.description = description;
    }

    public Cell[][] getCell() {

        return cell; //TODO implementare copia
    }

    public List<Room> getRoom() {

        return null; //TODO implementare
    }

    public String getDescription() {
        return description;
    }


    /**
     * compute distance from start to end according to Manhattan
     * @param start cell to start the distance
     * @param end cell to end the distance
     * @return distance
     */
    public int getDistance(Cell start, Cell end) {

        return 0; //TODO implementare
    }

    public Cell getCellByCoo(int x, int y){
        //TODO fai copia
        return cell[x][y];
    }

    public List<Cell> getCellAtDistance(Cell cellStart, int range) {
        return null;
    }

    /**
     * Return all cells in the direction indicated
     * @param cellStart Cell from which starts
     * @param NESW The direction (N - north, E - East, S - South, W - West)
     * @return If direction is correct return a list of cell in the direction
     * @throws IllegalArgumentException if direction doesn't exist
     */
    public List<Cell> getCellInDirection(Cell cellStart, char NESW) throws IllegalArgumentException{
        ArrayList<Cell> cells = new ArrayList<>();
        NESW = Character.toUpperCase(NESW);
        switch (NESW) {
            case 'N':
                for(int i = cellStart.getCoordinateY(); i < cell[cellStart.getCoordinateY()].length; i++) {
                    if(cell[cellStart.getCoordinateX()][i] == null) break;
                    cells.add(cell[cellStart.getCoordinateX()][i]);
                }
                break;
            case 'E':
                for(int i = cellStart.getCoordinateX(); i < cell.length; i++) {
                    if(cell[i][cellStart.getCoordinateY()] == null) break;
                    cells.add(cell[i][cellStart.getCoordinateY()]);
                }
                break;
            case 'S':
                for(int i = cellStart.getCoordinateY(); i >= 0; i--) {
                    if(cell[cellStart.getCoordinateX()][i] == null) break;
                    cells.add(cell[cellStart.getCoordinateX()][i]);
                }
                break;
            case 'W':
                for(int i = cellStart.getCoordinateX(); i >= 0; i--) {
                    if(cell[i][cellStart.getCoordinateY()] == null) break;
                    cells.add(cell[i][cellStart.getCoordinateY()]);
                }
                break;
                default: throw new IllegalArgumentException("You can only choose 4 direction: N - north, E - East, S - South, W - West");
        }
        return cells;
    }
}

class InitializeMap {
    Cell[][] cell;
    ArrayList<Room> room = new ArrayList<>();

    InitializeMap(int numOfRow, int numOfColumn) {
        cell = new Cell[numOfColumn][numOfRow];
    }
}

