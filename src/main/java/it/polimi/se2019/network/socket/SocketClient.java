package it.polimi.se2019.network.socket;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;
import it.polimi.se2019.view.clientView.ClientView;
import it.polimi.se2019.view.configureMessage.DisconnectMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient extends Client {
    private static final long serialVersionUID = -8669608992693984456L;
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
    public void connect() throws UnknownHostException {
        try {
            socket = new Socket(IP, port); /*Connection established*/
            createNewStream();
            unreferenced();

            new Thread(() -> {
                while (open) {
                    try {
                        HandlerNetworkMessage messageSocket = (HandlerNetworkMessage) scannerSocket.readObject();
                        messageSocket.handleMessage(this);
                    } catch (java.net.SocketException e) {
                        disconnect();
                    } catch (StreamCorruptedException | java.io.EOFException e) {
                        createNewStream();
                    } catch (java.net.SocketTimeoutException e) {
                        System.out.println("Your connection is slow, if something went wrong please retry.");
                    } catch (IOException | ClassNotFoundException e) {
                        Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Problem reading obj through socket", e);
                    }
                }
            }).start();

        } catch (IOException e) {
            throw new UnknownHostException();
        }
    }

    private void disconnect(){
        if(open) new DisconnectMessage().handleMessage(this);
        closeAll();
        open=false;
    }

    private void createNewStream() {
        try {
            if(open) {
                printSocket = new ObjectOutputStream(socket.getOutputStream());
                scannerSocket = new ObjectInputStream(socket.getInputStream());
            }
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * Forward a message from the client to the sever
     * @param message message to send
     */
    public void send(Object message) {
        try {
            if(open) {
                printSocket.writeObject(message);
                printSocket.flush();
            }
        } catch (IOException e) {
            new DisconnectMessage().handleMessage(this);
            open=false;
            closeAll();
        }
    }

    /**
     * Close all stream and disconnect this from the Server
     */
    @Override
    public void closeAll() {
        try {
            if(open) {
                socket.close();
                scannerSocket.close();
            }
        } catch (IOException e) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.FINE, "Can't close client socket", e);
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
