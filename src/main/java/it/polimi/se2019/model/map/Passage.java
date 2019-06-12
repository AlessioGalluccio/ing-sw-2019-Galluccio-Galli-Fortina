package it.polimi.se2019.model.map;

import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;

public class Passage implements Border {

    @Override
    public boolean isCrossable() {
        return true;
    }

    @Override
    public void printRow(int row) {
        //Can't print a row without know the direction
        //Use printByDirection
    }

    @Override
    public void printByDirection(int row, boolean horizontal, ConsoleColor color) {
        System.out.print(color);
        String space = ConsoleColor.WHITE_BOLD_BRIGHT + "▦" + color;
        if(horizontal) {
            System.out.print("▦");
            for(int i=1; i< Printable.DIMROW-1; i++){
                System.out.print(space);
            }
            System.out.print("▦");
        } else {
            if(row==0||row==Printable.DIMROW) System.out.print("▦");
            else System.out.print(space);
        }
    }
}
