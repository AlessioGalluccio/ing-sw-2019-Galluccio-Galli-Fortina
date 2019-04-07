package it.polimi.se2019.model.map;
import java.util.ArrayList;


//TODO rendere immutable
public abstract class Map extends java.util.Observable {
    private Cell[][] cell;
    private ArrayList<Room> room;
    private final String description;

    public Map(String description) {
        this.description = description;
        //TODO implementare

    }

    public ArrayList<Cell> getCell() {

        return null; //TODO implementare
    }

    public ArrayList<Room> getRoom() {

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

