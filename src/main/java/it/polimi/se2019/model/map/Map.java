package it.polimi.se2019.model.map;

//Immutable
public abstract class Map {
    private final ArrayList<Cell> cell;
    private final ArryList<Room> room;
    private final String description;

    public Map(String description) {
        this.description = description;

    }

    public ArrayList<Cell> getCell() {
        return cell.clone();
    }

    public ArryList<Room> getRoom() {
        return room.clone();
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

