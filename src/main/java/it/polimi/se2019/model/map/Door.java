package it.polimi.se2019.model.map;


import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;

import java.io.PrintWriter;

public class Door implements Border {

    @Override
    public boolean isCrossable(){
        return true;
    }

    @Override
    public void printRow( int row) {
        //Can't print a row without know the direction
        //Use printByDirection
    }

    @Override
    public void printByDirection( int row, boolean horizontal, ConsoleColor color) {
        System.out.print(color);
        String space = ConsoleColor.WHITE_BOLD_BRIGHT + "▦" + color;
        if(horizontal) {
                System.out.print("▦▦▦" +
                        ConsoleColor.WHITE_BOLD_BRIGHT + "▦▦▦" + color +
                        "▦▦▦");
        } else {
            if(row<3||row>Printable.DIMROW-6) System.out.print("▦");
            else System.out.print(space);
        }
    }

}
