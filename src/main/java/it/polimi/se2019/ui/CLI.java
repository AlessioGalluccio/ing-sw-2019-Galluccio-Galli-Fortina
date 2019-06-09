package it.polimi.se2019.ui;

import it.polimi.se2019.model.map.Map1;
import it.polimi.se2019.model.map.Map2;
import it.polimi.se2019.model.map.Map3;
import it.polimi.se2019.model.map.Map4;
import it.polimi.se2019.view.clientView.ClientView;

import java.util.Scanner;
import java.io.PrintWriter;

public class CLI implements UiInterface {
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
    public void selectedMap(int choosenMap) {
    }

    public void chooseSetting() {
        int map =1;
        do {
            out.println("Which map do you want?");
            out.println("\t1. " + Map1.getDescription());
            out.println("\t2. " + Map2.getDescription());
            out.println("\t3. " + Map3.getDescription());
            out.println("\t4. " + Map4.getDescription());
            map = in.nextInt();
        }while(map<1||map>4);

        out.println("How many skulls?");
        int skulls = in.nextInt();
        in.skip("\n");

        while(skulls<5 || skulls >8) {
            out.println("Choose a number between 5 and 8");
            skulls = in.nextInt();
            in.skip("\n");
        }

        int decision;
        boolean sd = false;
        do {
            out.println("Do you wanna play with sudden death?");
            out.println("\t 1. Yes");
            out.println("\t 2. No");
            decision = in.nextInt();
            if (decision == 1) {
                sd = true;
            } else if (decision == 2) {
                sd = false;
            }
        }while(decision<1||decision>2);

        view.createSettingMessage(map, skulls, sd);

        clearScreen();
        out.println("\nWaiting for other players...");
    }

    public synchronized void start() {
        out.println("__          __  _                            _       \n" +
                "\\ \\        / / | |                          (_)      \n" +
                " \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___   _ _ __  \n" +
                "  \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | | '_ \\ \n" +
                "   \\  /\\  /  __/ | (_| (_) | | | | | |  __/ | | | | |\n" +
                "    \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___| |_|_| |_|\n" +
                "                                                     \n");

        try {
            wait(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        printLogo();
        login();
    }

    private void printLogo() {
        out.println(ConsoleColor.RED_BOLD);
        out.print(":::'###::::'########::'########::'########:'##::: ##::::'###::::'##:::::::'####:'##::: ##:'########:\n" +
                "::'## ##::: ##.... ##: ##.... ##: ##.....:: ###:: ##:::'## ##::: ##:::::::. ##:: ###:: ##: ##.....::\n" +
                ":'##:. ##:: ##:::: ##: ##:::: ##: ##::::::: ####: ##::'##:. ##:: ##:::::::: ##:: ####: ##: ##:::::::\n" +
                "'##:::. ##: ##:::: ##: ########:: ######::: ## ## ##:'##:::. ##: ##:::::::: ##:: ## ## ##: ######:::\n" +
                " #########: ##:::: ##: ##.. ##::: ##...:::: ##. ####: #########: ##:::::::: ##:: ##. ####: ##...::::\n" +
                " ##.... ##: ##:::: ##: ##::. ##:: ##::::::: ##:. ###: ##.... ##: ##:::::::: ##:: ##:. ###: ##:::::::\n" +
                " ##:::: ##: ########:: ##:::. ##: ########: ##::. ##: ##:::: ##: ########:'####: ##::. ##: ########:\n" +
                "..:::::..::........:::..:::::..::........::..::::..::..:::::..::........::....::..::::..::........:: ");

        out.println(ConsoleColor.RESET);
    }

    private void login() {
        out.println("\n\n\n" +
                "Please, insert your name:");
        String username = in.next();

        int decision=0;
        int matchID = -1;
        do{
            out.println("Do you have a match ID?");
            out.println("\t 1. Yes");
            out.println("\t 2. No");

            decision = in.nextInt();
            in.skip("\n");
            if(decision == 1) {
                out.println("Enter the match ID");
                matchID = in.nextInt();
                in.skip("\n");
            }
        }while(decision<1||decision>2);

        view.createLoginMessage(username, matchID);
    }

    public static void clearScreen() {
        System.out.print("\u001b[2J");
        System.out.flush();
    }
}
