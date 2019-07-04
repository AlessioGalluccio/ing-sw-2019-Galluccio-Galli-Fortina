package it.polimi.se2019.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Galli
 */
public interface RmiServerInterface extends Remote {

    /**
     * Connect the Server to the client
     * @param client The client where to connect
     * @throws RemoteException If can't connect correctly due to RMI problems
     */
    void connect(RmiClientInterface client) throws RemoteException;
}
