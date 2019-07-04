package it.polimi.se2019.model.map;

import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;

import static it.polimi.se2019.ui.ConsoleSymbol.WALL;

/**
 * @author Galli
 */
public class Wall implements Border {

    private static final long serialVersionUID = -6579182187447608328L;

    /**
     * Return true if a player can move through it, false otherwise
     * @return False
     */
    @Override
    public boolean isCrossable() {
        return false;
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
     * Print a specific line of the wall in one direction (horizontal/vertical)
     * If the line is vertical will println only the char of that row
     * @param row the line to print
     * @param horizontal true if the line is horizontal, false if is vertical
     * @param color the color of the room
     * @return The string to print
     */
    @Override
    public String printByDirection(int row, boolean horizontal, ConsoleColor color) {
        String s = color.toString();
        if(horizontal) {
            for(int i = 0; i< Printable.DIMROW; i++){
                        s+= WALL;
            }
        } else {
            s+=WALL;
        }
        return  s;
    }

}
