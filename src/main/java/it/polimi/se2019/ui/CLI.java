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

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.*;
import java.io.PrintWriter;

public class CLI implements UiInterface {
    private final int MIN_SKULL = 1;
    boolean endGame;
    private ClientView view;
    private boolean online = true;
    private SkullBoardView skullBoardView;
    List<EnemyView> enemyViews = new LinkedList<>();
    private MapView mapView;
    private boolean yourTurn;
    private ParserCLI parser;

    @SuppressWarnings("squid:S106")
    private static PrintWriter out = new PrintWriter(new OutputStreamWriter(
            System.out, StandardCharsets.UTF_8), true);
    private Scanner in = new Scanner(System.in);

    public CLI(ClientView view) {
        this.view = view;
    }


    /**
     * Notify the users about the success of the login
     * @param success true if the login works correctly, false otherwise
     * @param isFirst true if the user is the first of the match, false otherwise
     */
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

    /**
     * otify the users about the starting of the game
     */
    @Override
    public void startGame() {
        clearScreen();
        out.println(ConsoleColor.WHITE_BRIGHT + "LET'S START! \t(๑•̀ㅂ•́)ง✧\n" + ConsoleColor.RESET);
        parser = new ParserCLI(view, this);
    }

    /**
     * Notify the users they were be disconnected from the match
     * @param matchID tha ID of the match they were playing
     */
    @Override
    public void disconnect(int matchID) {
        parser.open = false;
        online=false;
        clearScreen();
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

    /**
     * A usual setter for the skullBoardView attribute
     * @param skullBoardView the skullBoardView to set
     */
    @Override
    public void setSkullBoard(SkullBoardView skullBoardView) {
        this.skullBoardView = skullBoardView;
    }

    /**
     * A usual setter for the mapView attribute
     * @param mapView the mapView to set
     */
    @Override
    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    /**
     * A usual setter for the enemyView attribute
     * @param enemyView the enemyView to set
     */
    @Override
    public void setEnemyView(EnemyView enemyView) {
        enemyViews.add(enemyView);
    }

    /**
     * Print a string from the controller
     * @param message The message to print to the users
     */
    @Override
    public void printFromController(String message) {
        out.println("\n" + message);
    }

    /**
     * Print all the map or simply notify the users it has changed
     * @param cell the cell which has changed and has to be updated
     */
    @Override
    public void updateCell(Cell cell) {
        if(yourTurn) printMap();
        else out.println("Map has been updated. [Digit update map to see it]");
    }

    /**
     * Print all the player info or simply notify the users it has changed
     */
    @Override
    public void updatePlayer() {
        if(yourTurn) out.println(view.getPlayerCopy().toString());
        else out.println("You have been updated. [Digit update me to see it]");
    }

    /**
     * Print the enemy info or simply notify the users it has changed
     * @param enemyView the enemy to updte
     */
    @Override
    public void updateEnemy(ClientEnemyView enemyView) {
        if(yourTurn) out.println(enemyView.toStringShort());
        else out.println(enemyView.getNickname() + " has been updated. [Digit update enemy "
                +  enemyView.getNickname()+" to see it]");
    }

    /**
     * Print the map or simply notify the users the skull board has changed
     */
    @Override
    public void updateSkullBoard() {
        if(yourTurn) printMap();
        else out.println("The skull board has been updated. [Digit update map to see it]");
    }

    /**
     * Notify the users about the ending of the game
     * This method prints the ranking of the game
     * @param players The list of player on order of ranking
     */
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

    /**
     * Notify the users a new turn as begin
     * @param nickname The nickname of the player whose turn it is
     * @param yourTurn True if is your turn
     */
    @Override
    public void turn(String nickname, boolean yourTurn) {
        this.yourTurn = yourTurn;
        printAll();
        if(yourTurn) out.println(ConsoleColor.GREEN + "It's your turn! \t୧☉□☉୨" + ConsoleColor.RESET);
        else out.println("It's " + nickname + "'s turn \tಠᴗಠ");
    }

    /**
     * Notify the users they has to choose a character
     * @param characters a list of available character from which to choose
     */
    @Override
    public void chooseCharacter(List<Character> characters) {
        out.println("Choose your character form:");
        for(int i=1; i<=characters.size(); i++) {
            out.println("\t" + i + ". " + characters.get(i-1).toString());
        }
        out.println("[Digit 'character' follow by  the relative number]");
    }

    /**
     * Start the CLI.
     * Welcome the users, made they choose the connection and do he login
     */
    public void start() {
        clearScreen();
        out.println("\n" +
                "\t\t                  |                                   _)        \n" +
                "\t\t \\ \\  \\   /  _ \\  |   __|   _ \\   __ `__ \\    _ \\      |  __ \\  \n" +
                "\t\t  \\ \\  \\ /   __/  |  (     (   |  |   |   |   __/      |  |   | \n" +
                "\t\t   \\_/\\_/  \\___| _| \\___| \\___/  _|  _|  _| \\___|     _| _|  _| \n" +
                "\t\t                                                                ");

        printLogo();
        out.println("\n");
        printLine();
        setConnection();
        login();
    }

    /**
     * Let the users choose the type of connection they prefer
     */
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
                in.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
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

    /**
     * Print the logo of Adrenaline
     */
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

    /**
     * Let the users do the login
     */
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
               in.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
               if (decision == 1) {
                   out.println("Enter the match ID");
                   matchID = in.nextInt();
                   in.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
               }
           }catch (InputMismatchException e) {
               out.println("You can insert only 1 or 2");
               in.nextLine();
           }
        }while(decision<1||decision>2);

        view.createLoginMessage(username, matchID);
    }

    /**
     * Let the users choose the map, che number of skulls and f they wanna the frenzy or not
     */
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
                in.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
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
                in.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
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
                in.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
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

    /**
     * Clear the screen and put the cursor on the top of the screen
     */
    void clearScreen() {
        out.print("\033[2J \033[H");
        out.flush();
    }

    /**
     * Print a line long almost all the screen
     */
    void printLine() {
        out.println("______________________________________________________________________________________________\n");
    }

    /**
     * Print all the useful info for the users.
     * Print the skull board, the map, the enemies' info and the user's player's info
     */
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

    /**
     * Print a string follow by "\n"
     * @param string the string to print
     */
    void println(String string) {
        out.println(string);
    }

    /**
     * Print to string in two different column
     * @param left What has to be in the left column
     * @param right What has to be in the right column
     */
    void printf(String left, String right) {
        out.printf("%-90.90s %-115.115s%n", left, right);
    }

    /**
     * Print the map
     */
    void printMap() {
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
