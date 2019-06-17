package it.polimi.se2019.ui;

import it.polimi.se2019.view.View;

import java.util.Scanner;

public class ParserCLI {
    private View view;
    private boolean open;
    private Scanner in = new Scanner(System.in);

    ParserCLI(View view) {
        this.view = view;
        new Thread(() -> {
            while(open) {

            }
        }).start();
    }
}
