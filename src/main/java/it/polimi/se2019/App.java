package it.polimi.se2019;

import it.polimi.se2019.network.socket.SocketHandler;
import it.polimi.se2019.network.socket.SocketServer;
import it.polimi.se2019.ui.CLI;
import it.polimi.se2019.ui.ControllerLogin;
import it.polimi.se2019.view.clientView.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Hello world!
 */
public class App extends Application {
    private static SocketServer socketServer;
    private static RMIServer rmi;

    @Override
    public void start(Stage primaryStage) throws Exception{
        ControllerLogin.open("login.fxml", "Adrenaline",300,300);
    }


    public static void main(String[] args) {
        if(args[0].equalsIgnoreCase("SERVER")) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        App.class.getClassLoader().getResourceAsStream("config")));
                Properties p = new Properties();
                p.load(reader);

                int timerWait = Integer.parseInt(p.getProperty("timerWait"));
                int timerTurn = Integer.parseInt(p.getProperty("timerTurn"));

                RMIServer.start(7001, timerWait, timerTurn);
                socketServer = new SocketServer(9001, timerWait, timerTurn);
                socketServer.start();
            } catch (IOException e) {
                System.out.println("Can'r read properly the config file");
            }
        } else if(args[0].equalsIgnoreCase("CLIENT")) {
            if(args[1].equalsIgnoreCase("CLI")) {
                ClientView clientView = new ClientView();
                CLI cli = new CLI(clientView);
                clientView.setUi(cli);
                cli.start();
            } else {
                System.out.println("Starting with GUI");
                launch(args);
            }
        }
    }
}
