package it.polimi.se2019.model.map;

import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.ConsoleSymbol;
import it.polimi.se2019.ui.Printable;

import static it.polimi.se2019.ui.ConsoleSymbol.WALL;

public class Passage implements Border {

    private static final long serialVersionUID = -2039080465510300245L;

    /**
     * Return true if a player can move through it, false otherwise
     * @return True
     */
    @Override
    public boolean isCrossable() {
        return true;
    }

    /**
     * Can't print a row without know the direction
     * @param row the line to println
     * @return The string to print
     */
    @Override
    public String printRow(int row) {
        //Can't print a row without know the direction
        //Use printByDirection
        return "";
    }

    /**
     * Print a specific line of the passage in one direction (horizontal/vertical)
     * If the line is vertical will println only the char of that row
     * @param row the line to print
     * @param horizontal true if the line is horizontal, false if is vertical
     * @param color the color of the room
     * @return The string to print
     */
    @Override
    public String printByDirection(int row, boolean horizontal, ConsoleColor color) {
        String s = color.toString();
        String space = ConsoleColor.BLACK + WALL.toString() + color;
        if(horizontal) {
            s+=WALL;
            for(int i=1; i< Printable.DIMROW-1; i++){
                s+=space;
            }
            s+=WALL;
        } else {
            if(row==0||row==Printable.DIMROW) s+=WALL;
            else s+=space;
        }
        return s;
    }
}
