package it.polimi.se2019.model.map;

import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;

public class Passage implements Border {

    private static final long serialVersionUID = -2039080465510300245L;

    @Override
    public boolean isCrossable() {
        return true;
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
        String space = ConsoleColor.BLACK + "◙" + color;
        if(horizontal) {
            s+="◙";
            for(int i=1; i< Printable.DIMROW-1; i++){
                s+=space;
            }
            s+="◙";
        } else {
            if(row==0||row==Printable.DIMROW) s+="◙";
            else s+=space;
        }
        return s;
    }
}
