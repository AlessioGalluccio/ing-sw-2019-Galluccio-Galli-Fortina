package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;

import java.util.Scanner;

public class ClientView {

    private Player playerCopy;
    private Map mapCopy;



    public String send(){

        Scanner myObj = new Scanner(System.in);
        String userLine = myObj.nextLine();
        return userLine;

    }

}
