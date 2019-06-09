package it.polimi.se2019;

import it.polimi.se2019.network.socket.SocketClient;
import it.polimi.se2019.ui.CLI;
import it.polimi.se2019.view.clientView.ClientView;

public class ClientMain {

    public static void main( String[] args ) {
        ClientView clientView = new ClientView();


        CLI cli = new CLI(clientView);
        clientView.setUi(cli);

        SocketClient socket = new SocketClient(9001, "localhost", clientView);
        socket.connect();
        clientView.setUp(socket);
        cli.start();

    }
}
