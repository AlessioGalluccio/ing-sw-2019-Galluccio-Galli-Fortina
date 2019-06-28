package it.polimi.se2019.model.map;

import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;

public class Door implements Border {

    private static final long serialVersionUID = -4178550241484863775L;

    /**
     * Return true if a player can move through it, false otherwise
     * @return True
     */
    @Override
    public boolean isCrossable(){
        return true;
    }

    /**
     * Print a specific line of the door in one direction (horizontal/vertical)
     * If the line is vertical will println only the char of that row
     * @param row the line to print
     * @param horizontal true if the line is horizontal, false if is vertical
     * @param color the color of the room
     * @return The string to print
     */
    @Override
    public String printByDirection( int row, boolean horizontal, ConsoleColor color) {
        String s = color.toString();
        String space = ConsoleColor.BLACK + "◙" + color;
        if(horizontal) {
            s+="◙◙◙" +space+space+space+ "◙◙◙";
        } else {
            if(row<3||row>Printable.DIMROW-6) s+="◙";
            else s+=space;
        }
        return s;
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

}
