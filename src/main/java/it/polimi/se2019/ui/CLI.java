package it.polimi.se2019.ui;

import it.polimi.se2019.model.map.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.rmi.RMIClient;
import it.polimi.se2019.network.socket.SocketClient;
import it.polimi.se2019.view.clientView.ClientEnemyView;
import it.polimi.se2019.view.clientView.ClientView;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.*;
import java.io.PrintWriter;

public class CLI implements UiInterface {
    private final int MIN_SKULL = 5;
    public boolean endGame;
    private ClientView view;
    private boolean online = true;
    private SkullBoardView skullBoardView;
    List<EnemyView> enemyViews = new LinkedList<>();
    private MapView mapView;
    private boolean yourTurn;
    private ParserCLI parser;

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
    public void startGame() {
        out.println(ConsoleColor.WHITE_BRIGHT + "LET'S START! \t(๑•̀ㅂ•́)ง✧\n" + ConsoleColor.RESET);
        parser = new ParserCLI(view, this);
    }

    @Override
    public void disconnect(int matchID) {
        parser.open = false;
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

    @Override
    public void printFromController(String message) {
        out.println("\n" + message);
    }

    @Override
    public void updateCell(Cell cell) {
        if(yourTurn) printMap();
        else out.println("Map has been updated. [Digit update map to see it]");
    }

    @Override
    public void updatePlayer() {
        if(yourTurn) out.println(view.getPlayerCopy().toString());
        else out.println("You have been updated. [Digit update me to see it]");
    }

    @Override
    public void updateEnemy(ClientEnemyView enemyView) {
        if(yourTurn) out.println(enemyView.toStringShort());
        else out.println(enemyView.getNickname() + " has been updated. [Digit update enemy "
                +  enemyView.getNickname()+" to see it]");
    }

    @Override
    public void updateSkullBoard() {
        if(yourTurn) printMap();
        else out.println("The skull board has been updated. [Digit update map to see it]");
    }

    @Override
    public void printRanking(List<Player> players) {
        out.print("\n\n");
        printLine();
        out.println(ConsoleColor.MAGENTA_BOLD + "\t\tGAME OVER!\n" + ConsoleColor.RESET);
        out.printf("%-25.25s %d %s%n", "  1. " + players.get(0).getNickname(), players.get(0).getNumPoints(),
                ConsoleColor.GREEN + "\t(๑•̀ㅂ•́)ง✧" + ConsoleColor.RESET);
        for(int i=1; i<players.size(); i++) {
            out.printf("%-25.25s %d %n", "  "+i+". " + players.get(i).getNickname(), players.get(i).getNumPoints());
        }
        printLine();
        endGame = true;
    }

    @Override
    public void turn(String nickname, boolean yourTurn) {
        this.yourTurn = yourTurn;
        printAll();
        if(yourTurn) out.println(ConsoleColor.GREEN + "It's your turn! \t୧☉□☉୨" + ConsoleColor.RESET);
        else out.println("It's " + nickname + "'s turn \tಠᴗಠ");
    }

    @Override
    public void chooseCharacter(List<Character> characters) {
        out.println("Choose your character form:");
        for(int i=1; i<=characters.size(); i++) {
            out.println("\t" + i + ". " + characters.get(i-1).toString());
        }
        out.println("[Digit 'character' follow by  the relative number]");
    }

    public void start() {
        out.println("\n" +
                "\t\t                  |                                   _)        \n" +
                "\t\t \\ \\  \\   /  _ \\  |   __|   _ \\   __ `__ \\    _ \\      |  __ \\  \n" +
                "\t\t  \\ \\  \\ /   __/  |  (     (   |  |   |   |   __/      |  |   | \n" +
                "\t\t   \\_/\\_/  \\___| _| \\___| \\___/  _|  _|  _| \\___|     _| _|  _| \n" +
                "\t\t                                                                ");

        printLogo();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        out.println("\n");
        printLine();
        setConnection();
        login();
    }

    private void setConnection() {
        out.println("HI!  ｡◕‿◕｡ ");
        out.println("Please, insert the server's IP: ");
        String IP = in.nextLine();

        int decision=0;
        do{
            out.println("Which type of connection do you preferred?\n" +
                    "\t1. Socket" +
                    "\n\t2. RMI" +
                    "\n\t3. What are socket and RMI?");
            try {
                decision = in.nextInt();
                in.skip("\n");
                if (decision == 1||decision==3) {
                    SocketClient socket = new SocketClient(9001, IP, view);
                    socket.connect();
                    view.setUp(socket);
                } else if(decision==2) {
                    RMIClient rmi = new RMIClient(view, IP);
                    rmi.connect();
                    view.setUp(rmi);
                }
            } catch (InputMismatchException e) {
                out.println("You can insert only 1, 2 or 3");
                in.nextLine();
            } catch (RemoteException | java.net.UnknownHostException e) {
                out.println(ConsoleColor.RED + "Wrong IP, retry." + ConsoleColor.RESET);
                out.println("Please, insert the server's IP:");
                IP = in.nextLine();
                decision=0;
            }
        }while(decision<1||decision>3);
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
        out.println("\nPlease, enter your name:");
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
               out.println("You can insert only 1 or 2");
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
                out.println("You can insert only a digit");
                in.nextLine();
            }
        }while(map<1||map>4);

        int skulls=0;
        do{
            if(skulls!=0) out.println("You can insert only a digit between " + MIN_SKULL + " and 8" );
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
            if(decision!=0) out.println("You can insert only 1 or 2");
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
                out.println("You can insert only 1 or 2");
                in.nextLine();
            }
        }while(decision<1||decision>2);

        view.createSettingMessage(map, skulls, sd);

        if(online) out.println("\nWaiting for other players...");
    }

    void clearScreen() {
        out.print("\033[2J \033[H");
        out.flush();
    }

    void printLine() {
        out.println("______________________________________________________________________________________________\n");
    }

    void printAll() {
        clearScreen();
        printMap();
        printLine();
        for(EnemyView ew : enemyViews) {
            out.println(ew.toStringShort() + "\n");
        }
        printLine();
        out.println(view.getPlayerCopy().toString());
        printLine();
    }

    void println(String string) {
        out.println(string);
    }

    void printf(String left, String right) {
        out.printf("%-90.90s %-115.115s%n", left, right);
    }

    public void printMap() {
        out.println("");
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
    }
}
