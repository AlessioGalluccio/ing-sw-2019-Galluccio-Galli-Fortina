package it.polimi.se2019.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {
    void connect(RmiClientInterface client) throws RemoteException;
}
