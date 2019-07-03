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
    private static final long serialVersionUID = 7125169937302118373L;
    static RmiServerInterface remoteObj;
    private LinkedList<RmiHandlerInterface> handlers;
    private int timerTurn;
    private transient WaitingRoom waitingRoom;

    private RMIServer(int timerWaiting, int timerTurn) throws RemoteException {
        handlers = new LinkedList<>();
        this.waitingRoom = WaitingRoom.create(timerWaiting);
        this.timerTurn= timerTurn;
    }

    /**
     * Start the RMI sever
     * @param port Server's port used by client to connect
     * @param timerWaiting Duration of the timer during the login phase
     * @param timerTurn Duration of the timer for a single turn
     */
    public static void start(int port, int timerWaiting, int timerTurn) {
        try {
            RMIServer obj = new RMIServer(timerWaiting, timerTurn);
            unexportObject(obj,true);
            remoteObj = (RmiServerInterface) UnicastRemoteObject.exportObject(obj, port);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.rebind("RMIServer", remoteObj);

            Logger.getLogger(RMIServer.class.getName()).log(Level.INFO, "RMI server ready");
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

    /**
     * Usual getter for the WaitingRoom param
     * @return The Waiting Room object linked to this server
     */
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






