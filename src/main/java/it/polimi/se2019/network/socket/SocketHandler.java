package it.polimi.se2019.network.socket;

import it.polimi.se2019.network.configureMessage.HandlerConfigMessage;
import it.polimi.se2019.network.configureMessage.SwitchServerMessage;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;
import it.polimi.se2019.network.configureMessage.HandlerServerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketHandler implements Server, SwitchServerMessage {
    private Socket socket;
    private SocketServer server;
    private ObjectOutputStream printSocket;
    private ObjectInputStream scannerSocket;
    private Timer timer = new Timer();
    private int duration;
    private boolean open = true;
    private PlayerView view;

    SocketHandler(Socket socket, SocketServer server) {
        this.socket = socket;
        this.server = server;
        //TODO read duration from config file MOLTIPLICARE PER 1000!
    }

    /**
     * When a socket thread in created to handle a client, this handler listen to the socket
     */
    void start() {
        try {
            printSocket = new ObjectOutputStream(socket.getOutputStream());
            scannerSocket =  new ObjectInputStream(socket.getInputStream());

            new Thread(() -> {
                try {
                    while(open) {
                        //Receive message
                        HandlerServerMessage message = (HandlerServerMessage) scannerSocket.readObject();
                        message.handleMessage(this);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Problem receiving obj through socket", e);
                } finally {
                    //TODO send disconnect message
                    closeAll(); // Closing socket
                    open = false;
                }
            }).start();
        } catch (IOException e) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Scanner socket closed", e);
        }
    }

    /**
     * Close all stream and disconnect this from the Server
     */
    void closeAll() {
       try {
            scannerSocket.close();
            printSocket.close();
            socket.close();
        } catch (IOException e) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Scanner socket closed", e);
        } finally {
           open = false;
           server.disconnect(this);
       }
    }

    /**
     * Send to the client a string, typically send by controller
     * @param string text to send
     */
    @Override
    public void send(String string) {
        //TODO rifare con un messaggio opportuno
        try {
            printSocket.writeObject(string);
            printSocket.flush();
        }catch (IOException e) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Problem sending obj through socket", e);
        }
    }

    /**
     * Forward a message from model to the client thought socket connection
     * @param message message to send
     */

    //CANCELLIAMO????
    @Override
    public void send(ModelViewMessage message) {
       send((Object) message);
    }

    private void send(Object message) {
        try {
            printSocket.writeObject(message);
            printSocket.flush();
        }catch (IOException e) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Problem sending obj through socket", e);
        }
    }

    /**
     * Set a new timer for the socket, when it expires disconnect the client from the server
     * The duration of the timer is read from a config file
     */
    @Override
    public void setTimer() {
        if(timer==null) timer = new Timer();
        else timer.cancel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO send disconnect message
                closeAll();
            }
        }, duration);
    }

    /**
     * Terminates the timer set on this socket, discarding any currently scheduled tasks.
     *
     * <p>This method may be called repeatedly; the second and subsequent
     * calls have no effect.
     */
    @Override
    public void cancelTimer() {
        timer.cancel();
        timer = null;
    }

    /**
     * Receive a message from the view and forward it to the client
     * @param o Will be always null
     * @param arg the message to send to the client
     */
    @Override
    public void update(Observable o, Object arg) {
        send(arg);
    }

    @Override
    public void forwardConfigMessage(HandlerServerMessage message) {
        server.waitingRoom.receiveMessage((HandlerConfigMessage) message, this);
    }

    @Override
    public void forwardViewMessage(HandlerServerMessage message) {

    }
}
