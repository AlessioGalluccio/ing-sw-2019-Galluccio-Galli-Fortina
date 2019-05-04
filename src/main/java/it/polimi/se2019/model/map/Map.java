package it.polimi.se2019.model.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public abstract class Map extends java.util.Observable implements Serializable {
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

    public Cell getCellByCoo(int x, int y){
        //TODO fai copia
        return cell[x][y];
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
}

class InitializeMap {
    Cell[][] cell;
    ArrayList<Room> room = new ArrayList<>();

    InitializeMap(int numOfRow, int numOfColumn) {
        cell = new Cell[numOfColumn][numOfRow];
    }
}

