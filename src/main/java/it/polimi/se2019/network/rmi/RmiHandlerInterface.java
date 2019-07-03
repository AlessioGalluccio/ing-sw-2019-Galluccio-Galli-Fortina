package it.polimi.se2019.network.rmi;


import it.polimi.se2019.network.messages.HandlerServerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiHandlerInterface extends Remote {

    /**
     * Send a message from the Client to the Handler
     * @param message The message send to the server
     * @throws RemoteException If can't send correctly the message due to connection problems
     */
    void send(HandlerServerMessage message) throws RemoteException;
}
