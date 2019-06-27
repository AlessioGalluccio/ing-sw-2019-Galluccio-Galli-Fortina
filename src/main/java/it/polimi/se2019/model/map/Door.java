package it.polimi.se2019.model.map;


import it.polimi.se2019.ui.ConsoleColor;
import it.polimi.se2019.ui.Printable;

import java.io.PrintWriter;

public class Door implements Border {

    private static final long serialVersionUID = -4178550241484863775L;

    @Override
    public boolean isCrossable(){
        return true;
    }


    @Override
    public String printByDirection( int row, boolean horizontal, ConsoleColor color) {
        String s = color.toString();
        String space = ConsoleColor.BLACK + "▦" + color;
        if(horizontal) {
            s+="▦▦▦" +space+space+space+ "▦▦▦";
        } else {
            if(row<3||row>Printable.DIMROW-6) s+="▦";
            else s+=space;
        }
        return s;
    }

    @Override
    public String printRow(int row) {
        //Can't print a row without know the direction
        //Use printByDirection
        return "";
    }

}
