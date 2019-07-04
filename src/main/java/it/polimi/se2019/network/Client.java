package it.polimi.se2019.network;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;
import it.polimi.se2019.view.clientView.ClientEnemyView;
import it.polimi.se2019.view.clientView.ClientMapView;
import it.polimi.se2019.view.clientView.ClientSkullBoardView;
import it.polimi.se2019.view.clientView.ClientView;

import java.net.UnknownHostException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observer;
import java.util.List;
import java.util.LinkedList;

/**
 * @author Galli
 */
public abstract class Client extends UnicastRemoteObject implements Observer {

    private static final long serialVersionUID = -1040745061995072394L;
    protected ClientView clientView;
    private ClientSkullBoardView skullBoardView;
    private List<ClientEnemyView> enemyViews = new LinkedList<>();
    private ClientMapView mapView;
    private UiInterface ui;
    private String ID;

    protected Client() throws RemoteException {
    }

    /**
     * Connect client to server
     * @throws UnknownHostException if the IP of the socket server is not correct
     * @throws RemoteException if the IP of the RMI server is not correct
     */
    public abstract void connect() throws UnknownHostException, RemoteException;

    /**
     * Forward the status of login at the UI.
     * @param success True if login is successful, false otherwise
     * @param isFirst True if the user is the first of the match and must choose the map
     * @param nickname nickname of the user who has logged in
     */
    public void handleLoginMessage(boolean success, boolean isFirst, String nickname) {
        if(success) this.ID = nickname;
        clientView.handleLogin(success, isFirst);
    }

    /**
     * Forward the message to the client view object
     * @param message The message to forward
     * @param nickname The player's nickname who has generate the message
     */
    public void forwardToClientView(ModelViewMessage message, String nickname) {
        synchronized (clientView) {
            if (nickname.equals(ID) || nickname.equalsIgnoreCase("BROADCAST")) {
                clientView.update(null, message);
                clientView.notifyAll();
            }
        }
    }

    /**
     * Create an EnemyView object for the player with the specified nickname
     * @param nickname Enemy's nickname
     */
    public synchronized void handleEnemyMessage(String nickname) {
        enemyViews.add(new ClientEnemyView(nickname, ui));
        notifyAll();
    }

    /**
     * Notify the View the user has been disconnected and close the client
     */
    public void handleDisconnection() {
        clientView.handleDisconnection();
        closeAll();
    }

    /**
     * Notify the View the game is started.
     * This method is executed when all the views has been set correctly.
     * @param matchID The match's ID which has started
     */
    public void handleStartGame(int matchID) {
        synchronized (clientView) {
            //If views are not initialized wait
            while (mapView.getMapCopy() == null ||
                    skullBoardView.getLastAck() == -1 ||
                    clientView.getPlayerCopy() == null ||
                    (!enemyViews.isEmpty() && enemyViews.get(0).getID() == -1) ||
                    (enemyViews.size()>1 && enemyViews.get(1).getID() == -1) ||
                    (enemyViews.size()>2 && enemyViews.get(2).getID() == -1) ||
                    (enemyViews.size()>3 && enemyViews.get(3).getID() == -1)) {
                try {
                    clientView.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            clientView.handleStartGameMessage(matchID);
        }
    }

    /**
     * Forward the message to the correct enemy view object
     * @param message The message to forward
     * @param nickname The player's nickname who has generate the message
     */
    public void forwardToEnemyView(ModelViewMessage message, String nickname) {
        synchronized (clientView) {
            for (ClientEnemyView ew : enemyViews) {
                if (ew.getNickname().equals(nickname)) ew.update(null, message);
            }
            clientView.notifyAll();
        }
    }

    /**
     * Forward the message to the map view object
     * @param message The message to forward
     */
    public void forwardToMapView(ModelViewMessage message) {
        synchronized (clientView) {
            mapView.update(null, message);
            clientView.notifyAll();
        }
    }

    /**
     * Forward the message to the skull board view object
     * @param message The message to forward
     */
    public void forwardToSkullBoardView(ModelViewMessage message) {
        skullBoardView.update(null, message);
    }

    /**
     * Set the ui param of this object.
     * Set the Client param of SkullBoardView and MapView.
     * @param ui The UI object to set
     */
    public void setUpUi(UiInterface ui) {
        this.ui = ui;
        skullBoardView = new ClientSkullBoardView(ui);
        mapView = new ClientMapView(ui);
    }

    /**
     * It is used by the server to send string to the client with the rmi connection
     * @param string the string send by the controller
     */
    public void printFromController(String string) {
        clientView.printFromController(string);
    }


    /**
     * Shutdown correctly an RMI client
     * @throws NoSuchObjectException If the object has already been unreferenced
     */
    protected void unreferenced() throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(this, true);
    }

    /**
     * Close all streams open and terminate all threads in order to disconnect correctly a client
     */
    public abstract void closeAll();

    /**
     * Forward to the view the list of possible characters form which a user can choose
     * @param character The list of possible characters.
     */
    public void handleCharacterMessage(List<Character> character) {
        clientView.setPossibleCharacter(character);
    }
}
