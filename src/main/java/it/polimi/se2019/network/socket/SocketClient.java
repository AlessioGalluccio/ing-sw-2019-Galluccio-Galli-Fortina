package it.polimi.se2019.network.socket;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.clientView.ClientView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient extends Client {
    private int port;
    private String IP;
    private boolean open;
    private ObjectOutputStream printSocket;
    private ObjectInputStream scannerSocket;
    private Socket socket;

    public SocketClient(int port, String IP, ClientView view) throws RemoteException {
        this.port = port;
        this.IP = IP;
        this.open = true;
        this.clientView = view;
    }

    /**
     * Connect the client to the server
     */
    @Override
    public void connect() {
        try {
            socket = new Socket(IP, port); /*Connection established*/
            printSocket = new ObjectOutputStream(socket.getOutputStream());
            scannerSocket =  new ObjectInputStream(socket.getInputStream());

            new Thread(() -> {
                try {
                    while(open) { // Fake condition: it's always true
                        HandlerNetworkMessage messageSocket = (HandlerNetworkMessage) scannerSocket.readObject();
                        System.out.println("MESSAGE IN");
                        messageSocket.handleMessage(this);
                    }
                }catch (IOException | ClassNotFoundException e) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.INFO, "Problem reading from socket", e);
                }
                finally {
                    closeAll();
                    open = false;
                }
            }).start();

        } catch (IOException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.WARNING, "Can't open client socket", e);
        }
    }

    /**
     * Forward a message from the client to the sever
     * @param message message to send
     */
    public void send(Object message) {
        try {
            printSocket.writeObject(message);
            printSocket.flush();
        } catch (IOException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.WARNING, "Problem writing on socket", e);
        }
    }

    /**
     * Close all stream and disconnect this from the Server
     */
    private void closeAll() {
        try {
            printSocket.close();
            scannerSocket.close();
            socket.close();
        } catch (IOException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.WARNING, "Can't close client socket", e);
        } finally {
            open = false;
        }
    }

    /**
     * Receive a message from the view and forward it to the server
     * @param o Will be always null
     * @param arg the message to send to the sever
     */
    @Override
    public void update(Observable o, Object arg) {
        send(arg);
    }
}
