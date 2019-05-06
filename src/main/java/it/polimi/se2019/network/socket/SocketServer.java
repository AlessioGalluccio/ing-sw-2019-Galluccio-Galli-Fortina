package it.polimi.se2019.network.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.util.logging.*;

public class SocketServer {
    private int port;
    private boolean open;
    private ArrayList<SocketThread> threads = new ArrayList<>();

    public SocketServer(int port) {
        this.port = port;
        this.open = true;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(open) { // Fake condition: it's always true
                Socket socket = serverSocket.accept();
                SocketThread handleClient = new SocketThread(socket);
                threads.add(handleClient);
                handleClient.start();
                //Server ready on port this.port
            }
        } catch (IOException e) { //Go here if serverSocket is closed
            Logger.getLogger(SocketServer.class.getName()).log(Level.WARNING, "ServerSocket close", e);
        } finally {
            for (SocketThread s : threads) {
                s.closeAll();
            }
            threads.clear();
        }
    }
}

