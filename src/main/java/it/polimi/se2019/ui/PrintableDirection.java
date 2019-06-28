package it.polimi.se2019.ui;


public interface PrintableDirection extends Printable{

    /**
     * Print a specific line of the object in one direction(horizontal/vertical)
     * If the line is vertical will println only the char of that row
     * @param row the line to println
     * @param horizontal true if the line is horizontal, false if is vertical
     * @param color the color of the object
     * @return The string to print
     */
    String printByDirection(int row, boolean horizontal, ConsoleColor color);
}
