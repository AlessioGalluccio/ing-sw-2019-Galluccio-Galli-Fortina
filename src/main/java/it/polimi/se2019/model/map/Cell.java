package it.polimi.se2019.model.map;

import it.polimi.se2019.model.Observable;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;
import it.polimi.se2019.view.ModelViewMess.CellModelMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Cell extends Observable implements Printable, Target, Serializable {

    private Border northBorder;
    private Border eastBorder;
    private Border southBorder;
    private Border westBorder;
    private Room room;
    private Coordinate coordinate;
    private ArrayList<Player> playerHere = new ArrayList<>();
    private boolean disable;

    protected Cell(Border north, Border east, Border south, Border west, int x, int y) {
        this.northBorder = north;
        this.eastBorder = east;
        this.southBorder = south;
        this.westBorder = west;
        this.coordinate = new Coordinate(x,y);
        this.disable = false;
    }

    protected Cell(Border north, Border east, Border south, Border west, int x, int y, boolean disable) {
        this.northBorder = north;
        this.eastBorder = east;
        this.southBorder = south;
        this.westBorder = west;
        this.coordinate = new Coordinate(x,y);
        this.disable = true;
    }

    public int getCoordinateX() { return coordinate.getX(); }

    public int getCoordinateY() {
        return coordinate.getY();
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
     * Return a shallow copy of the player on this cell
     * @return list of players on this cell
     */
    public List<Player> getPlayerHere() {
        return new ArrayList<>(playerHere);
    }

    /**
     * Each cell has some card on it, this method grab a card
     * @param cardID ID of the card I wanna grab (ONLY FOR THOSE CELL THAT HAS MORE CARD ON IT) --Not CellAmmo
     * @return the card on the cell
     * @throws NotCardException If card has already taken or there not a card with the ID
     */
    public abstract Card grabCard(int cardID) throws NotCardException;

    /**
     * Each cell has some card on it, this method grab a card and replace that with another one
     * @param cardID ID of the card I wanna grab (ONLY FOR THOSE CELL THAT HAS MORE CARD ON IT)
     * @param card The card to replace with
     * @return the card on the cell
     * @throws NotCardException If card has already taken or there not a card with the ID
     */
    public abstract Card grabCard(int cardID, WeaponCard card) throws NotCardException;

    /**
     * Each cell has some card on it, this method return a list of that cards' IDs
     * @return the IDs of the cards on this cell
     */
    public abstract List<Integer> getCardID();

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    protected abstract void reloadCard();

    /**
     * Each cell has to have a room, this method set the appropriate room
     * @param room room of this cell
     * @throws AlreadyRoomException if a room was already set for this cell, it can't be modified!
     */
    protected void setRoom(Room room) {
        if(this.room==null) this.room = room;
        else throw new AlreadyRoomException("This cell " + this +" has already a room!");
    }

    /**
     * When a player is moved in this cell, he is added to a list
     * @param playerToAdd the new player in the cell to add
     * @throws NotHereException the player position is not this cell
     */
    public void addPlayer(Player playerToAdd) {
        //Add here the player ONLY IF its position is this cell
        if (playerToAdd.getCell().getCoordinateX() == coordinate.getX() &&
                playerToAdd.getCell().getCoordinateY() == coordinate.getY()) {
            playerHere.add(playerToAdd);
            room.addPlayer(playerToAdd);
            notifyObservers(new CellModelMessage(this.clone()));
        } else throw new NotHereException(playerToAdd.toString() + " is not here " +toString());
    }

    /**
     * When a player is moved from this cell, he is removed from the list
     * @param playerToRemove the player who was moved
     * @throws NotHereException the player was not moved from here
     */
    public void removePlayer(Player playerToRemove) {
        if (playerToRemove.getCell().getCoordinateX() == coordinate.getX() &&
                playerToRemove.getCell().getCoordinateY() == coordinate.getY()) {
            playerHere.remove(playerToRemove);
            room.removePlayer(playerToRemove);
            notifyObservers(new CellModelMessage(this.clone()));
        }
        else throw new NotHereException(playerToRemove.toString() + " was not here " +toString());
    }

    @Override
    public String toString() {
        return "Cell{" +
                "X=" + coordinate.getX() +
                ", Y=" + coordinate.getY() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return northBorder.getClass() == cell.northBorder.getClass() &&
                eastBorder.getClass() == cell.eastBorder.getClass()&&
                southBorder.getClass() == cell.southBorder.getClass() &&
                westBorder.getClass()== cell.westBorder.getClass()&&
                //Objects.equals(room, cell.room) &&
                coordinate.equals(cell.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(northBorder, eastBorder, southBorder, westBorder, coordinate);
    }

    public abstract Cell clone();

    /**
     * Print this cell on System.out line by line
     * @param row the line to print
     */
    @Override
    public String printRow(int row) {
        String s ="";
        ConsoleColor color = ConsoleColor.colorByColor(getRoom().getColor());
        String space = ConsoleColor.WHITE_BRIGHT + "▦" + color;
        if(!isDisable()) s+=color;
        switch (row) {
            case 0:
                s+=getNorthBorder().printByDirection(0, true, color);
                break;
            case 1:
                s+=getWestBorder().printByDirection(1, false, color);
                s+=printPlayerOnCell(1, 2);
                s+=getEastBorder().printByDirection(1, false, color);
                break;
            case 2:
                s+=getWestBorder().printByDirection(2, false, color);
                s+=space + space;
                s+=space;
                if(getPlayerHere().size()==5) {
                    s+=ConsoleColor.colorByColor(getPlayerHere().get(4).getCharacter().getColor());
                    s+="◉";
                }else s+=space;
                s+=space;
                s+=space + space;
               s+= getEastBorder().printByDirection(2, false, color);
                break;
            case 3:
                s+=printMiddleRow();
                break;
            case 4:
                s+=getWestBorder().printByDirection(4, false, color);
                s+=space+space+space+space+space+space+space;
                s+=getEastBorder().printByDirection(4, false, color);
                break;
            case 5:
                s+= getWestBorder().printByDirection(5, false, color);
                s+=printPlayerOnCell(3, 4);
                s+=getEastBorder().printByDirection(5, false,color);
                break;
            case 6:
                s+=getSouthBorder().printByDirection(6, true, color);
                break;
            default:
        }
       s+=ConsoleColor.RESET;
        return s;
    }


    /**
     * If this cell have some player on it, will be print two of them in this row
     * @param player1 the number of the first player that will be print
     * @param player2 the number of the second player that will be print
     */
    private String printPlayerOnCell(int player1, int player2) {
        String s ="";
        ConsoleColor color = ConsoleColor.colorByColor(getRoom().getColor());
        String space = ConsoleColor.WHITE_BOLD_BRIGHT + "▦" + color;
        s+=space + space;
        if(getPlayerHere().size()==player1) {
            s+=ConsoleColor.colorByColor(getPlayerHere().get(player1-1).getCharacter().getColor());
            s+="◉";
        }else s+=space;
        s+=space;
        if(getPlayerHere().size()==player2) {
            s+=ConsoleColor.colorByColor(getPlayerHere().get(player2-1).getCharacter().getColor());
            s+="◉";
        }else s+=space;
        s+=space + space;
        return s;
    }

    /**
     * Print the row in the middle of the cell
     */
    abstract String printMiddleRow();

    boolean isDisable() {
        return disable;
    }
}
