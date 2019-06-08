package it.polimi.se2019;

import it.polimi.se2019.network.socket.SocketServer;

/**
 * Hello world!
 */
public class App {
    private static SocketServer socketServer;
    //private static RMIserver rmi;

    public static void main( String[] args ) {
        socketServer = new SocketServer(9001, 20, 90);
        socketServer.start();
    }
}
