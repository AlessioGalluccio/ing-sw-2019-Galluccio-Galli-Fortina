package it.polimi.se2019.ui;

public interface Printable {
    int DIMROW = 9;

    /**
     * Print a specific line of the object
     * @param row the line to println
     */
    String printRow(int row);
}
