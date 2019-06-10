package it.polimi.se2019.network.rmi;

import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.se2019.RMIServer;
import it.polimi.se2019.network.HandlerNetworkMessage;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.network.configureMessage.HandlerConfigMessage;
import it.polimi.se2019.network.configureMessage.HandlerServerMessage;
import it.polimi.se2019.network.configureMessage.SwitchServerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.rmi.RemoteException;
import java.util.Observer;

public class RmiHandler extends UnicastRemoteObject implements Observer, RmiHandlerInterface, Server, SwitchServerMessage {
    private RMIServer server;
    private Timer timer = new Timer();
    private RmiClientInterface client;
    private int duration;
    private PlayerView view;
    private ExecutorService executor;

    public RmiHandler(RmiClientInterface client, int duration, RMIServer server) throws RemoteException  {
        this.client = client;
        this.duration = duration*1000;
        this.server = server;
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        executor.submit(new Sender((HandlerNetworkMessage) arg));
    }

    @Override
    public void setPlayerView(PlayerView pw) {
        view = pw;
    }

    @Override
    public void send(String string) {

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

    @Override
    public void send(HandlerServerMessage message) throws RemoteException {
        message.handleMessage(this);
    }

    @Override
    public void forwardConfigMessage(HandlerServerMessage message) {
        server.getWaitingRoom().receiveMessage((HandlerConfigMessage) message, this);
    }

    @Override
    public void forwardViewMessage(HandlerServerMessage message) {

    }

    private class Sender implements Runnable {
        HandlerNetworkMessage message;

        Sender(HandlerNetworkMessage message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                client.receiveMessage(message);
            } catch (RemoteException e) {
                Logger.getLogger(RmiHandler.class.getName()).log(Level.SEVERE, "Can't send message to RMI client", e);
            }
        }
    }
}
