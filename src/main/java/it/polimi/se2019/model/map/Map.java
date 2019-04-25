package it.polimi.se2019.model.map;

import java.util.ArrayList;
import java.util.List;

public abstract class Map extends java.util.Observable {
    private final Cell[][] cell;
    private final ArrayList<Room> room;
    private final String description;

    Map(InitializeMap init, String description) {
        this.cell = init.cell;
        this.room = init.room;
        this.description = description;
    }

    public List<Cell> getCell() {

        return null; //TODO implementare
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
}

class InitializeMap {
    Cell[][] cell;
    ArrayList<Room> room = new ArrayList<>();

    InitializeMap(int numOfRow, int numOfColumn) {
        cell = new Cell[numOfRow][numOfColumn];
    }
}

