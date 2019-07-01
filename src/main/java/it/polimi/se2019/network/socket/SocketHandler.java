package it.polimi.se2019.network.socket;

import it.polimi.se2019.network.messages.HandlerConfigMessage;
import it.polimi.se2019.network.messages.PrintFromControllerMessage;
import it.polimi.se2019.network.messages.SwitchServerMessage;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.network.messages.HandlerServerMessage;
import it.polimi.se2019.view.ViewControllerMess.ReconnectionMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.configureMessage.DisconnectMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketHandler implements Runnable, Server, SwitchServerMessage {
    private Socket socket;
    private SocketServer server;
    private ObjectOutputStream printSocket;
    private ObjectInputStream scannerSocket;
    private Timer timer = new Timer();
    private int duration;
    private boolean open = true;
    private PlayerView view;
    private int matchID = 0;

    SocketHandler(Socket socket, SocketServer server, int duration) {
        this.socket = socket;
        this.server = server;
        this.duration = duration*1000;
    }

    /**
     * When a socket thread in created to handle a client, this handler listen to the socket
     */
    @Override
    public void run() {
            createNewStream();
        while(open) {
            try {
                //Receive message
                HandlerServerMessage message = (HandlerServerMessage) scannerSocket.readObject();
                message.handleMessage(this);
            } catch (java.net.SocketException e) {
                if(open) disconnect();
            } catch (java.io.EOFException | StreamCorruptedException e) {
                createNewStream();
            } catch (java.net.SocketTimeoutException e) {
                view.printFromController("Your connection is slow, if something went wrong please retry.");
            }catch (ClassNotFoundException | IOException e) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Problem receiving obj through socket", e);
            }
        }
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
     * Close all stream and disconnect this from the Server
     */
    public void closeAll() {
       try {
           open = false;
           timer.cancel();
           scannerSocket.close();
           socket.close();
        } catch (IOException e) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Scanner socket closed", e);
       }
    }

    @Override
    public void setPlayerView(PlayerView pw) {
        view = pw;
    }

    /**
     * Send to the client a string, typically send by controller
     * @param string text to send
     */
    @Override
    public void send(String string) {
        try {
            if(open) {
                printSocket.writeObject(new PrintFromControllerMessage(string));
                printSocket.flush();
            }
        }catch (IOException e) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Problem sending obj through socket", e);
            disconnect();
        }
    }

    /**
     * Forward a message from model to the client thought socket connection
     * @param message message to send
     */
    private void send(Object message) {
        try {
            if(open) {
                printSocket.writeObject(message);
                printSocket.flush();
            }
        }catch (IOException e) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.WARNING, "Problem sending obj through socket", e);
            disconnect();
        }
    }

    /**
     * Set a new timer for the socket, when it expires disconnect the client from the server
     * The duration of the timer is read from a config file
     */
    @Override
    public void setTimer() {
        if(timer!=null) timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                send(new DisconnectMessage());
                disconnect();
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
        if(timer!=null) timer.cancel();
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
    public void forwardViewMessage(ViewControllerMessage message) {
        message.setView(view);
        view.notifyObservers(message);
    }

    private void disconnect() {
        open = false;
        closeAll();
        server.waitingRoom.disconnect(this, view, matchID);
        //If match is started disconnect form controller
        if(matchID!=0) view.notifyObservers(new ReconnectionMessage(false, view.getPlayerCopy().getID(), view));
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }


}
