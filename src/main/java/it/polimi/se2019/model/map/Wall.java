package it.polimi.se2019.model.map;

import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;

import java.io.PrintWriter;

public class Wall implements Border {

    @Override
    public boolean isCrossable() {
        return false;
    }

    @Override
    public void printRow(int row) {
        //Can't print a row without know the direction
        //Use printByDirection
    }

    @Override
    public void printByDirection(int row, boolean horizontal, ConsoleColor color) {
        System.out.print(color);
        if(horizontal) {
            for(int i = 0; i< Printable.DIMROW; i++){
                System.out.print("▦");
            }
        } else {
            System.out.print("▦");
        }
    }

}
