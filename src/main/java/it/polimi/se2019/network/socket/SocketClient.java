package it.polimi.se2019.network.socket;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient implements Client {
    private int port;
    private String IP;
    private boolean open;
    private ObjectOutputStream printSocket;
    private ObjectInputStream scannerSocket;
    private Socket socket;

    public SocketClient(int port, String IP) {
        this.port = port;
        this.IP = IP;
        this.open = true;
    }

    @Override
    public void connect() {
        try {
            socket = new Socket(IP, port); /*Connection established*/
            printSocket = new ObjectOutputStream(socket.getOutputStream());
            scannerSocket =  new ObjectInputStream(socket.getInputStream());

            new Thread(() -> {
                try {
                    while(open) { // Fake condition: it's always true
                        Object messageSocket = scannerSocket.readObject();
                        //TODO parse the messagge
                    }
                }catch (IOException | ClassNotFoundException e) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.INFO, "Problem reading from socket", e);
                }
                finally {
                    closeAll();
                }
            }).start();

        } catch (IOException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.WARNING, "Can't open client socket", e);
        }
    }

    @Override
    public void send(ViewControllerMessage message) {
        try {
            printSocket.writeObject(message);
            printSocket.flush();
        } catch (IOException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.WARNING, "Problem writing on socket", e);
        }
    }

    private void closeAll() {
        try {
            printSocket.close();
            scannerSocket.close();
            socket.close();
        } catch (IOException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.WARNING, "Can't close client socket", e);
        }
    }
}
