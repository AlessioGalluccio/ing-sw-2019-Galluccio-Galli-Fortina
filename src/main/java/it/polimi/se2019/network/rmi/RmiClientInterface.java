package it.polimi.se2019.network.rmi;

import it.polimi.se2019.network.HandlerNetworkMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiClientInterface extends Remote {

    /**
     * Receive a message from the RmiHandler and handle him correctly
     * @param message The message received from the server
     * @throws RemoteException If can't receive correctly the message due to connection problems
     */
    void receiveMessage(HandlerNetworkMessage message) throws RemoteException;

    /**
     * Connect the Client to the server
     * @param server The server where to connect
     * @throws RemoteException If can't connect correctly due to RMI problems
     */
    void connect(RmiHandlerInterface server) throws RemoteException;

    /**
     * Every few millisecond a ping message is send form the server.
     * If something goes wrong and a ping can't be send the connection will be closed.
     * @throws RemoteException If can't ping correctly due to RMI problems
     */
    void ping() throws RemoteException;

}
