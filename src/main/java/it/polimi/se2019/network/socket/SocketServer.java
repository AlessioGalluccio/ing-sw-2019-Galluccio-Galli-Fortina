package it.polimi.se2019.network.socket;


import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;
import java.net.ServerSocket;
import java.util.logging.*;

public class SocketServer {
    private int port;

    public SocketServer(int port) {
        this.port = port;
    }

    public void start() {
        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(!executor.isTerminated()) { // Fake condition: it's always true
                Socket socket = serverSocket.accept();
                executor.submit(new SocketThread(socket));
                //Server ready on port this.port
            }
        } catch (IOException e) { //Go here if serverSocket is closed
            Logger.getLogger("it.polimi.se2019.network.socket").log(Level.WARNING, "Socket close", e);
        }
        executor.shutdown();
    }
}

