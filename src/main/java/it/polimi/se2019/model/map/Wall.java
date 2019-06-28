package it.polimi.se2019.model.map;

import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;

import java.io.PrintWriter;

public class Wall implements Border {

    private static final long serialVersionUID = -6579182187447608328L;

    @Override
    public boolean isCrossable() {
        return false;
    }

    @Override
    public String printRow(int row) {
        //Can't print a row without know the direction
        //Use printByDirection
        return "";
    }

    @Override
    public String printByDirection(int row, boolean horizontal, ConsoleColor color) {
        String s = color.toString();
        if(horizontal) {
            for(int i = 0; i< Printable.DIMROW; i++){
                        s+="◙";
            }
        } else {
            s+="◙";
        }
        return  s;
    }

}
