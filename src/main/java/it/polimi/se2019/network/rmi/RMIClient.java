package it.polimi.se2019.network.rmi;

import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;
import it.polimi.se2019.network.configureMessage.HandlerServerMessage;
import it.polimi.se2019.view.clientView.ClientView;

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
    private RmiHandlerInterface server;
    private ExecutorService executor;

    public RMIClient(ClientView view) throws RemoteException {
        super();
        this.clientView = view;
    }

    @Override
    public void connect() {
        executor = Executors.newCachedThreadPool();
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
            RmiServerInterface stub = (RmiServerInterface) registry.lookup("RMIServer");
            stub.connect(this);
        } catch (RemoteException |  NotBoundException e) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, "Problem connecting to RMI server", e);
        }
    }


    @Override
    public void update(Observable o, Object arg) {
       executor.submit(new Sender((HandlerServerMessage) arg));
    }

    @Override
    public void receiveMessage(HandlerNetworkMessage message) throws RemoteException {
        message.handleMessage(this);
    }

    @Override
    public void connect(RmiHandlerInterface server) throws RemoteException {
        this.server = server;

    }

    private class Sender implements Runnable {
        HandlerServerMessage message;

        Sender(HandlerServerMessage message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                server.send(message);
            } catch (RemoteException e) {
                Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, "Can't send message to RMI server", e);
            }finally {
                System.out.println("EXIT SENDER");
            }
        }
    }
}