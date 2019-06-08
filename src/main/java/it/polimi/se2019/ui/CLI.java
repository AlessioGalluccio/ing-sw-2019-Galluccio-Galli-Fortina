package it.polimi.se2019.ui;

import it.polimi.se2019.view.clientView.ClientView;

import java.util.Scanner;
import java.io.PrintWriter;

public class CLI implements UiInterface {
    private ClientView view;
    private ParserCLI parser; //TODO crearlo dopo il login

    @SuppressWarnings("squid:S106")
    private PrintWriter out = new PrintWriter(System.out, true);
    private Scanner in = new Scanner(System.in);

    public CLI(ClientView view) {
        this.view = view;
    }


    @Override
    public void login(boolean success, boolean isFirst) {
        if(success) out.println("Success!" +
                "\n Waiting for other players...");
        else {
            out.println(ConsoleColor.RED + "Error! Nickname already in use" + ConsoleColor.RESET);
            login();
        }
        if(isFirst) chooseSetting();
    }

    @Override
    public void selectedMap(int choosenMap) {

    }

    public void chooseSetting() {
        out.println("Which map do you want?");
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
        out.println("Do you have a match ID?");
        out.println("\t 1. Yes");
        out.println("\t 2. No");

        int asw = in.nextInt();
        int matchID = -1;
        if(asw == 1) {
            out.println("Enter the match ID");
            matchID = in.nextInt();
        }

        view.createLoginMessage(username, matchID);
    }
}
