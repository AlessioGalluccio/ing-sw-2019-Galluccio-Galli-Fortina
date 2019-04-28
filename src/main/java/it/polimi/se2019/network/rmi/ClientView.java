package it.polimi.se2019.network.rmi;

import java.util.Scanner;

public class ClientView {

    public String send(){

        Scanner myObj = new Scanner(System.in);
        String userLine = myObj.nextLine();
        return userLine;

    }

}
