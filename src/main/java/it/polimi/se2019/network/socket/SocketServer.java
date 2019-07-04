package it.polimi.se2019.network.socket;

import it.polimi.se2019.network.WaitingRoom;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.*;
import java.util.concurrent.*;

/**
 * @author Galli
 */
public class SocketServer {
    private int port;
    private boolean open = true;
    private ExecutorService threads = Executors.newCachedThreadPool();
    WaitingRoom waitingRoom;
    private int timerTurn;

    public SocketServer(int port, int timerWaiting, int timerTurn) {
        this.port = port;
        this.waitingRoom = WaitingRoom.create(timerWaiting);
        this.timerTurn = timerTurn;
    }

    /**
     * Start the server
     */
    public void start() {
        Logger.getLogger(SocketServer.class.getName()).log(Level.INFO, "Socket server ready");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (open) {
                Socket socket = serverSocket.accept();
                threads.submit(new SocketHandler(socket, this, timerTurn));
                //Server ready on port this.port
            }
        } catch (IOException e) { //Go here if serverSocket is closed
            Logger.getLogger(SocketServer.class.getName()).log(Level.WARNING, "ServerSocket close", e);
        } finally {
            open = false;
            threads.shutdown();
        }
    }
}

