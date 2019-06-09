package it.polimi.se2019;

import it.polimi.se2019.model.deck.WeaponDeck;
import it.polimi.se2019.network.socket.SocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;

/**
 * Hello world!
 */
public class App {
    private static SocketServer socketServer;
    //private static RMIserver rmi;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader( new InputStreamReader(
                    WeaponDeck.class.getClassLoader().getResourceAsStream("config")));
            Properties p = new Properties();
            p.load(reader);
            if (p.getProperty("side").equals("SERVER")) {
                int timerWait = Integer.parseInt(p.getProperty("timerWait"));
                int timerTurn = Integer.parseInt(p.getProperty("timerTurn"));

                System.out.println("timerTurn: " + timerTurn);
                System.out.println("timerWait: " + timerWait);
                socketServer = new SocketServer(9001, timerWait, timerTurn);
                socketServer.start();
            }
        } catch (IOException e) {
            System.out.println("Error while reading config file");
            e.printStackTrace();
        }
    }
}
