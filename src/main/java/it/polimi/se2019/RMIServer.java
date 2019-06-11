package it.polimi.se2019;

import it.polimi.se2019.network.WaitingRoom;
import it.polimi.se2019.network.rmi.RmiClientInterface;
import it.polimi.se2019.network.rmi.RmiHandler;
import it.polimi.se2019.network.rmi.RmiHandlerInterface;
import it.polimi.se2019.network.rmi.RmiServerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServer extends UnicastRemoteObject implements RmiServerInterface {
    static RmiServerInterface remoteObj;
    private static final long serialVersionUID = 1L;
    private LinkedList<RmiHandlerInterface> handlers;
    private int timerTurn;
    private transient WaitingRoom waitingRoom;

    private RMIServer(int timerWaiting, int timerTurn) throws RemoteException {
        handlers = new LinkedList<>();
        this.waitingRoom = WaitingRoom.create(timerWaiting);
        this.timerTurn= timerTurn;
    }

    public static void start(int port, int timerWaiting, int timerTurn) {
        try {
            RMIServer obj = new RMIServer(timerWaiting, timerTurn);
            unexportObject(obj,true);
            remoteObj = (RmiServerInterface) UnicastRemoteObject.exportObject(obj, port);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.rebind("RMIServer", remoteObj);

            Logger.getLogger(RMIServer.class.getName()).log(Level.FINER, "Server ready");
        } catch (Exception e) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, "Problem starting RMI server", e);
        }
    }

    @Override
    public void connect(RmiClientInterface client) throws RemoteException {
        RmiHandlerInterface handler = new RmiHandler(client, timerTurn, this);
        this.handlers.add(handler);
        client.connect(handler);
    }

    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    /**
     * Disconnect a dead handler of a client from the Server
     * @param handler the handler who has died
     */
    public void disconnect(RmiHandlerInterface handler) {
        handlers.remove(handler);
    }

}






