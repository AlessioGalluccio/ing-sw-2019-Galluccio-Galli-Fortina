package it.polimi.se2019;

import it.polimi.se2019.ui.Controller;

import java.util.Scanner;

public class MyThread extends Thread {
    Controller controller;
    public MyThread(Controller controller) {
        super();
        this.controller = controller;
    }

    public void run(){

        while(true) {
            try {

                Scanner scanner = new Scanner(System.in);

                //System.out.println("qualcosa");
                controller.printf(scanner.nextLine());
            } catch(Exception e) {
            }
        }
    }
}
