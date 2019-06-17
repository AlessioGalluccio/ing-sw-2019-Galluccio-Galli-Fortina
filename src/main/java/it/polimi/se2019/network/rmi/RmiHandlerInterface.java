package it.polimi.se2019.network.rmi;


import it.polimi.se2019.network.messages.HandlerServerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiHandlerInterface extends Remote {
    void send(HandlerServerMessage message) throws RemoteException;
}
