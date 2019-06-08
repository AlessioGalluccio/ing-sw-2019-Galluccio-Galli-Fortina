package it.polimi.se2019.network.socket;

import it.polimi.se2019.network.WaitingRoom;
import org.mockito.exceptions.verification.WantedButNotInvoked;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.util.logging.*;

public class SocketServer {
    private int port;
    private boolean open = true;
    private ArrayList<SocketHandler> threads = new ArrayList<>();
    protected WaitingRoom waitingRoom;

    public SocketServer(int port, int timerWaiting, int timerTurn) {
        this.port = port;
        this.waitingRoom = WaitingRoom.create(timerWaiting);
    }

    /**
     * Start the server
     */
    public void start() {
        System.out.println("Server ready");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(open) {
                Socket socket = serverSocket.accept();
                SocketHandler handleClient = new SocketHandler(socket, this);
                threads.add(handleClient);
                handleClient.start();
                //Server ready on port this.port
            }
        } catch (IOException e) { //Go here if serverSocket is closed
            Logger.getLogger(SocketServer.class.getName()).log(Level.WARNING, "ServerSocket close", e);
        } finally {
            open = false;
            for (SocketHandler s : threads) {
                s.closeAll();
            }
            threads.clear();
        }
    }

    /**
     * Disconnect a dead handler of a client from the Server
     * @param socket the handler who has died
     */
     void disconnect(SocketHandler socket) {
        threads.remove(socket);
     }
}

