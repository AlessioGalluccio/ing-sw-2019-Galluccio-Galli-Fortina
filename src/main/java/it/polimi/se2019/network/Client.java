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

    public void handleLoginMessage(boolean success, boolean isFirst, String nickname) {
        if(success) this.ID = nickname;
        clientView.handleLogin(success, isFirst);
    }

    public void forwardToClientView(ModelViewMessage message, String nickname) {
        synchronized (clientView) {
            if (nickname.equals(ID) || nickname.equalsIgnoreCase("BROADCAST")) {
                clientView.update(null, message);
                clientView.notifyAll();
            }
        }
    }

    public synchronized void handleEnemyMessage(String nickname) {
        enemyViews.add(new ClientEnemyView(nickname, ui));
        notifyAll();
    }

    public void handleDisconnection() {
        clientView.handleDisconnection();
        closeAll();
    }

    public void handleStartGame(int matchID) {
        synchronized (clientView) {
            //If views are not initialized wait
            while (mapView.getMapCopy() == null ||
                    skullBoardView.getLastAck() == -1 ||
                    clientView.getPlayerCopy() == null ||
                    enemyViews.get(0).getNickname() == null ||
                    enemyViews.get(1).getNickname() == null) {
                try {
                    clientView.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        clientView.handleStartGameMessage(matchID);
    }

    public void forwardToEnemyView(ModelViewMessage message, String nickname) {
        synchronized (clientView) {
            for (ClientEnemyView ew : enemyViews) {
                if (ew.getNickname().equals(nickname)) ew.update(null, message);
            }
            clientView.notifyAll();
        }
    }

    public void forwardToMapView(ModelViewMessage message) {
        synchronized (clientView) {
            mapView.update(null, message);
            clientView.notifyAll();
        }
    }

    public void forwardToSkullBoardView(ModelViewMessage message) {
        skullBoardView.update(null, message);
    }

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


    protected void unreferenced() throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(this, true);
    }

    public abstract void closeAll();

    public void handleCharacterMessage(List<Character> character) {
        clientView.setPossibleCharacter(character);
    }
}
