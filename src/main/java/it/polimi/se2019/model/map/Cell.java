package it.polimi.se2019.model.map;

public abstract class Cell {
    private final Coordinate coordinate;
    private Border northBorder;
    private Border eastBorder;
    private Border southBorder;
    private Border westBorder;
    private ArrayList<Player> playerHere;
    private Room room;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Border getNorthBorder() {
        return northBorder;
    }

    public Border getEastBorder() {
        return eastBorder;
    }

    public Border getSouthBorder() {
        return southBorder;
    }

    public Border getWestBorder() {
        return westBorder;
    }

    public Room getRoom() {
        return room;
    }

    /**
     *
     * @return list of players on this cell
     */
    public ArrayList<Player> getPlayerHere() {

    }

    /**
     *
     * @return the card on the cell
     */
    public Card grabCard() {    //abstract

    }

    public void reloadCard() {  //abstract

    }

}
