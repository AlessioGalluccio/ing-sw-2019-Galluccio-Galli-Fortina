package it.polimi.se2019.ui;

import it.polimi.se2019.model.map.Map1;
import it.polimi.se2019.model.map.Map2;
import it.polimi.se2019.model.map.Map3;
import it.polimi.se2019.model.map.Map4;
import it.polimi.se2019.view.clientView.ClientView;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.PrintWriter;

public class CLI implements UiInterface {
    private final static int MIN_SKULL = 1;
    private ClientView view;
    private ParserCLI parser; //TODO crearlo dopo il login

    @SuppressWarnings("squid:S106")
    private static PrintWriter out = new PrintWriter(System.out, true);
    private Scanner in = new Scanner(System.in);

    public CLI(ClientView view) {
        this.view = view;
    }


    @Override
    public void login(boolean success, boolean isFirst) {
        if(success) {
            out.println(ConsoleColor.GREEN + "You're in!\n" +ConsoleColor.RESET);if(isFirst) chooseSetting();
            else {
                clearScreen();
                out.println("Waiting for other players...");
            }
        }

        else {
            out.println(ConsoleColor.RED + "Error! Nickname already in use\n" + ConsoleColor.RESET);
            login();
        }
    }

    @Override
    public void selectedMap(int chosenMap) {
    }

    @Override
    public void startGame() {
        out.println(ConsoleColor.BLACK + "START" + ConsoleColor.RESET);
    }

    public synchronized void start() {
        out.println("\n" +
                "\t\t                  |                                   _)        \n" +
                "\t\t \\ \\  \\   /  _ \\  |   __|   _ \\   __ `__ \\    _ \\      |  __ \\  \n" +
                "\t\t  \\ \\  \\ /   __/  |  (     (   |  |   |   |   __/      |  |   | \n" +
                "\t\t   \\_/\\_/  \\___| _| \\___| \\___/  _|  _|  _| \\___|     _| _|  _| \n" +
                "\t\t                                                                ");

        printLogo();
        try {
            wait(1300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        out.println("\n\n_____________________________________________________________________________________________");
        login();
    }

    private void printLogo() {
        out.println(ConsoleColor.RED_BOLD);
        out.print("\t\t\t/\\                                                                         \n" +
                "\t\t      \\     __ \\    _ \\   ____|   \\  |     \\     |     _ _|   \\  |  ____| \n" +
                "\t\t    __ \\    |   |  |   |  __|      \\ |    _ \\    |       |     \\ |  __|   \n" +
                "\t\t   ____ \\   |   |  __ <   |      |\\  |   ___ \\   |       |   |\\  |  |     \n" +
                "\t\t _/    __\\ ____/  _| \\_\\ _____| _| \\_| _/    _\\ _____| ___| _| \\_| _____| \n" +
                "\t\t                                                                         ");
        out.println(ConsoleColor.RESET);
    }

    private void login() {
        out.println("\n" +
                "Please, enter your name:");
        String username = in.nextLine();

        int decision=0;
        int matchID = -1;
        do{
           try {
               out.println("Do you have a match ID?");
               out.println("\t1. Yes");
               out.println("\t2. No");

               decision = in.nextInt();
               in.skip("\n");
               if (decision == 1) {
                   out.println("Enter the match ID");
                   matchID = in.nextInt();
                   in.skip("\n");
               }
           }catch (InputMismatchException e) {
               out.println("You can't insert only 1 or 2");
               in.nextLine();
           }
        }while(decision<1||decision>2);

        view.createLoginMessage(username, matchID);
    }

    private void chooseSetting() {
        int map =1;
        do {
            try{
                out.println("Which map do you want?");
                out.println("\t1. " + Map1.getDescription());
                out.println("\t2. " + Map2.getDescription());
                out.println("\t3. " + Map3.getDescription());
                out.println("\t4. " + Map4.getDescription());
                map = in.nextInt();
            }catch (InputMismatchException e) {
                out.println("You can't insert only a digit");
                in.nextLine();
            }
        }while(map<1||map>4);

        out.println("How many skulls?");
        int skulls = in.nextInt();
        in.skip("\n");

        while(skulls<MIN_SKULL|| skulls >8) {
            out.println("Choose a number between " + MIN_SKULL + " and 8");
            skulls = in.nextInt();
            in.skip("\n");
        }

        int decision = 0;
        boolean sd = false;
        do {
            try {
                out.println("Do you wanna play with sudden death?");
                out.println("\t1. Yes");
                out.println("\t2. No");
                decision = in.nextInt();
                if (decision == 1) {
                    sd = true;
                } else if (decision == 2) {
                    sd = false;
                }
            }catch (InputMismatchException e) {
                out.println("You can't insert only 1 or 2");
                in.nextLine();
            }
        }while(decision<1||decision>2);

        view.createSettingMessage(map, skulls, sd);

        //clearScreen();
        out.println("\nWaiting for other players...");
    }

    public static void clearScreen() {
        System.out.print("\u001b[2J");
        System.out.flush();
    }
}
