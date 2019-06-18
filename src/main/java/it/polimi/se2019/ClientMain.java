package it.polimi.se2019;

import it.polimi.se2019.network.rmi.RMIClient;
import it.polimi.se2019.network.socket.SocketClient;
import it.polimi.se2019.ui.CLI;
import it.polimi.se2019.view.clientView.ClientView;

import java.rmi.RemoteException;

public class ClientMain {

    public static void main( String[] args ) throws RemoteException {

        ClientView clientView = new ClientView();
        CLI cli = new CLI(clientView);
        clientView.setUi(cli);

        RMIClient rmi = new RMIClient(clientView, "localhost");
        rmi.connect();

        //SocketClient socket = new SocketClient(9001, "localhost", clientView);
        //socket.connect();
        clientView.setUp(rmi);
        cli.start();

    }
}
