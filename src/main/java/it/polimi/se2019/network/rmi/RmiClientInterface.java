package it.polimi.se2019.network.rmi;

import it.polimi.se2019.network.HandlerNetworkMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientInterface extends Remote {

    void receiveMessage(HandlerNetworkMessage message) throws RemoteException;

    void connect(RmiHandlerInterface server) throws RemoteException;

    void ping() throws RemoteException;

}
