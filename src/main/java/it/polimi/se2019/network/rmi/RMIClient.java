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
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Galli
 * @author Fortina
 */
public class RMIClient extends Client implements RmiClientInterface, Observer {
    private static final long serialVersionUID = 7523008212736615992L;
    private transient RmiHandlerInterface server;
    private String IP;

    public RMIClient(ClientView view, String IP) throws RemoteException {
        this.clientView = view;
        this.IP = IP;
    }

    @Override
    public void connect() throws RemoteException {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(IP, Registry.REGISTRY_PORT);
            RmiServerInterface stub = (RmiServerInterface) registry.lookup("RMIServer");
            stub.connect(this);
        } catch ( NotBoundException e) {
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
            new DisconnectMessage().handleMessage(this);
        }
    }

    @Override
    public void receiveMessage(HandlerNetworkMessage message) throws RemoteException {
        message.handleMessage(this);
    }

    @Override
    public void connect(RmiHandlerInterface server) throws RemoteException {
        this.server = server;
    }

    @Override
    public void ping() throws RemoteException {
        return;
    }
}