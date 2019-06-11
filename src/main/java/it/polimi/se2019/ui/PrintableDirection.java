package it.polimi.se2019.ui;


public interface PrintableDirection extends Printable{

    /**
     * Print a specific line of the object in one direction(horizontal/vertical)
     * If the line is vertical will print only the char of that row
     * @param row the line to print
     * @param horizontal true if the line is horizontal, false if is vertical
     */
    void printByDirection(int row, boolean horizontal, ConsoleColor color);
}
