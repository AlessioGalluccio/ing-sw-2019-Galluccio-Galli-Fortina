package it.polimi.se2019.network.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;
import java.net.ServerSocket;
import java.util.logging.*;

public class SocketServer {
    private int port;
    private boolean open;

    public SocketServer(int port) {
        this.port = port;
        this.open = true;
    }

    public void start() {
        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(open) { // Fake condition: it's always true
                Socket socket = serverSocket.accept();
                executor.submit(new SocketThread(socket));
                //Server ready on port this.port
            }
        } catch (IOException e) { //Go here if serverSocket is closed
            Logger.getLogger(SocketServer.class.getName()).log(Level.WARNING, "Socket close", e);
        }
        executor.shutdown();
    }
}

