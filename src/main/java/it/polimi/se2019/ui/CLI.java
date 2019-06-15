package it.polimi.se2019.ui;

import it.polimi.se2019.model.map.Map1;
import it.polimi.se2019.model.map.Map2;
import it.polimi.se2019.model.map.Map3;
import it.polimi.se2019.model.map.Map4;
import it.polimi.se2019.view.clientView.ClientView;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.InputMismatchException;
import java.util.*;
import java.io.PrintWriter;

public class CLI implements UiInterface {
    private final int MIN_SKULL = 5;
    private ClientView view;
    private boolean online = true;
    private SkullBoardView skullBoardView;
    private List<EnemyView> enemyViews = new LinkedList<>();
    private MapView mapView;
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
                if(online) out.println("Waiting for other players...");
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
        printAll();
    }

    @Override
    public void disconnect(int matchID) {
        //TODO chiudere tutti i thread (parser) e terminare applicazione
        online=false;
        out.println("\n");
        printLine();
        out.println(ConsoleColor.RED +
                "OH NO!  ಥ_ಥ \nYou have been disconnected" +
                ConsoleColor.RESET);
        out.println("If the game has started and you want to reconnect to the same match remember this ID: "
                + ConsoleColor.BLACK_BOLD + matchID + ConsoleColor.RESET );
        out.println("\nThank you for playing at:");
        printLogo();
    }

    @Override
    public void setSkullBoard(SkullBoardView skullBoardView) {
        this.skullBoardView = skullBoardView;
    }

    @Override
    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public void setEnemyView(EnemyView enemyView) {
        enemyViews.add(enemyView);
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
        out.println("\n");
        printLine();
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
        out.println("Please, enter your name:");
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
        int map = 0;
        do {
            if(map!=0) out.println("You can choose only between 1-4");
            try{
                out.println("Which map do you want?");
                out.println("\t1. " + Map1.getDescription());
                out.println("\t2. " + Map2.getDescription());
                out.println("\t3. " + Map3.getDescription());
                out.println("\t4. " + Map4.getDescription());
                map = in.nextInt();
                in.skip("\n");
            }catch (InputMismatchException e) {
                out.println("You can't insert only a digit");
                in.nextLine();
            }
        }while(map<1||map>4);

        int skulls=0;
        do{
            if(skulls!=0) out.println("You can't insert only a digit between " + MIN_SKULL + " and 8" );
            try {
                out.println("How many skulls?");
                skulls = in.nextInt();
                in.skip("\n");
            }catch (InputMismatchException e) {
                out.println("You can't insert only a digit between " + MIN_SKULL + " and 8" );
                in.nextLine();
            }
        }while(skulls<MIN_SKULL|| skulls >8);

        int decision = 0;
        boolean sd = false;
        do {
            if(decision!=0) out.println("You can't insert only 1 or 2");
            try {
                out.println("Do you wanna play with sudden death?");
                out.println("\t1. Yes");
                out.println("\t2. No");
                decision = in.nextInt();
                in.skip("\n");
                if (decision == 1) {
                    sd = true;
                }
            }catch (InputMismatchException e) {
                out.println("You can't insert only 1 or 2");
                in.nextLine();
            }
        }while(decision<1||decision>2);

        view.createSettingMessage(map, skulls, sd);

        //clearScreen();
        if(online) out.println("\nWaiting for other players...");
    }

    void clearScreen() {
        out.print("\u001b[2J");
        out.flush();
    }

    void printLine() {
        out.println("______________________________________________________________________________________________\n");
    }

    void printAll() {
        clearScreen();
        out.println("\t" + skullBoardView);
        out.println("");
        out.println(mapView.printRow(2,0));
        out.println(mapView.printRow(2,1));
        out.println(mapView.printRow(2,2));
        out.println(mapView.printRow(2,3));
        out.println(mapView.printRow(2,4));
        out.println(mapView.printRow(2,5));
        out.println(mapView.printRow(2,6));
        out.println(mapView.printRow(1,0));
        out.println(mapView.printRow(1,1));
        out.println(mapView.printRow(1,2));
        out.println(mapView.printRow(1,3));
        out.println(mapView.printRow(1,4));
        out.println(mapView.printRow(1,5));
        out.println(mapView.printRow(1,6));
        out.println(mapView.printRow(0,0));
        out.println(mapView.printRow(0,1));
        out.println(mapView.printRow(0,2));
        out.println(mapView.printRow(0,3));
        out.println(mapView.printRow(0,4));
        out.println(mapView.printRow(0,5));
        out.println(mapView.printRow(0,6));
        out.println(mapView.printRow(-1,0));
        printLine();
        for(EnemyView ew : enemyViews) {
            out.println(ew.toStringShort() + "\n");
        }
        printLine();
        out.println(view.getPlayerCopy().toString());
        printLine();
    }
}
