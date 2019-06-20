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
import it.polimi.se2019.network.messages.HandlerConfigMessage;
import it.polimi.se2019.network.messages.HandlerServerMessage;
import it.polimi.se2019.network.messages.PrintFromControllerMessage;
import it.polimi.se2019.network.messages.SwitchServerMessage;
import it.polimi.se2019.view.ViewControllerMess.ReconnectionMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.configureMessage.DisconnectMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.rmi.RemoteException;
import java.util.Observer;

public class RmiHandler extends UnicastRemoteObject implements Observer, RmiHandlerInterface, Server, SwitchServerMessage {
    private static final long serialVersionUID = 3131880418047594201L;
    private RMIServer server;
    private transient Timer timer = new Timer();
    private RmiClientInterface client;
    private int duration;
    private PlayerView view;
    private ExecutorService executor;
    private static int nThreads =0;
    private int matchID =0;
    private Timer pingTimer;

    public RmiHandler(RmiClientInterface client, int duration, RMIServer server) throws RemoteException  {
        this.client = client;
        this.duration = duration*1000;
        this.server = server;
        executor = Executors.newCachedThreadPool();
        pingTimer = new Timer();
        pingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    client.ping();
                } catch (RemoteException e) {
                    disconnect();
                }
            }
        }, 5000, 700);
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
        executor.submit(new Sender(new PrintFromControllerMessage(string)));
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
                new Sender(new DisconnectMessage());
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
        timer.cancel();
        timer = null;
    }

    @Override
    public void setMatchID(int matchID) {
        this.matchID = matchID;
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
    public void forwardViewMessage(ViewControllerMessage message) {
        message.setView(view);
        view.notifyObservers(message);
    }

    @Override
    public void closeAll() {
        pingTimer.cancel();
        executor.shutdown();
        server.disconnect(this);
    }

    private void disconnect() {
        closeAll();
        server.getWaitingRoom().disconnect(this, view, matchID);
        //If match is started disconnect form controller
        if(matchID!=0) view.notifyObservers(new ReconnectionMessage(false, view.getPlayerCopy().getID(), view));
    }

    private class Sender implements Runnable {
        HandlerNetworkMessage message;

        Sender(HandlerNetworkMessage message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                RmiHandler.nThreads++;
                client.receiveMessage(message);
                RmiHandler.nThreads--;
                Logger.getLogger(RmiHandler.class.getName()).log(Level.FINE, "Threads running: " + nThreads);
            } catch (RemoteException e) {
                Logger.getLogger(RmiHandler.class.getName()).log(Level.INFO, "Can't send message to RMI client." +
                        " It's close.", e);
                disconnect();
            }
        }
    }
}
