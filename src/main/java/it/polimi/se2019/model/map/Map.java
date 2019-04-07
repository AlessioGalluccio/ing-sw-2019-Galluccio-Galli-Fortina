package it.polimi.se2019.model.map;
import java.util.ArrayList;


//Immutable
public abstract class Map extends java.util.Observable {
    private final Cell[][] cell;
    private final ArrayList<Room> room;
    private final String description;

    public Map(String description) {
        this.description = description;

    }

    public ArrayList<Cell> getCell() {

    }

    public ArrayList<Room> getRoom() {

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

    }


}

