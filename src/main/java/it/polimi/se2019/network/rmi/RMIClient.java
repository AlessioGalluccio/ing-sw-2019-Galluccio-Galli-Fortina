package it.polimi.se2019.network.rmi;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;
import it.polimi.se2019.network.messages.HandlerServerMessage;
import it.polimi.se2019.view.clientView.ClientView;
import it.polimi.se2019.view.configureMessage.DisconnectMessage;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.Observable;
import java.util.concurrent.*;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClient extends Client implements RmiClientInterface, Observer {
    private static final long serialVersionUID = 7523008212736615992L;
    private RmiHandlerInterface server;
    private ExecutorService executor;
    static int nThreads =0;
    private String IP;

    public RMIClient(ClientView view, String IP) throws RemoteException {
        super();
        this.clientView = view;
        this.IP = IP;
    }

    @Override
    public void connect() {
        executor = Executors.newCachedThreadPool();
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(IP, Registry.REGISTRY_PORT);
            RmiServerInterface stub = (RmiServerInterface) registry.lookup("RMIServer");
            stub.connect(this);
        } catch (RemoteException |  NotBoundException e) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, "Problem connecting to RMI server", e);
        }
    }

    @Override
    public void closeAll() {
        try {
            unreferenced();
        } catch (NoSuchObjectException e) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.WARNING, "Can't shutdown RMI", e);
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        try {
            server.send((HandlerServerMessage) arg);
        } catch (RemoteException e) {
           // Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, "Can't send message to RMI server", e);
            new DisconnectMessage().handleMessage(this);
            executor.shutdown();
        }
    }

    @Override
    public void receiveMessage(HandlerNetworkMessage message) throws RemoteException {
        executor.submit(new Receiver(message, this));
    }

    @Override
    public void connect(RmiHandlerInterface server) throws RemoteException {
        this.server = server;
    }

    @Override
    public void ping() throws RemoteException {
        return;
    }

    private class Receiver implements Runnable {
        HandlerNetworkMessage message;
        RMIClient client;

        Receiver(HandlerNetworkMessage message, RMIClient client) {
            this.message = message;
            this.client = client;
        }

        @Override
        public void run() {
            RMIClient.nThreads++; //For testing
            message.handleMessage(client);
            RMIClient.nThreads--;
            Logger.getLogger(RMIClient.class.getName()).log(Level.FINE, "Threads running: " + nThreads);
        }
    }
}