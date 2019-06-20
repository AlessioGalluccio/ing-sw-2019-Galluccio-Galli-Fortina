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

    public synchronized void forwardToClientView(ModelViewMessage message, String nickname) {
        if(nickname.equals(ID) || nickname.equalsIgnoreCase("BROADCAST")) {
            clientView.update(null, message);
            notifyAll();
        }
    }

    public void handleEnemyMessage(String nickname) {
        enemyViews.add(new ClientEnemyView(nickname, ui));
    }

    public void handleDisconnection() {
        clientView.handleDisconnection();
        closeAll();
    }

    public synchronized void handleStartGame(int matchID) {
        //If views are not initialized wait
        while (mapView.getMapCopy() == null ||
                clientView.getPlayerCopy() == null ||
                enemyViews.get(0).getNickname() == null ||
                enemyViews.get(1).getNickname() == null) {
            try {
                wait();
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
            }
        }
        clientView.handleStartGameMessage(matchID);
    }

    public synchronized void forwardToEnemyView(ModelViewMessage message, String nickname) {
        for(ClientEnemyView ew : enemyViews) {
            if(ew.getNickname().equals(nickname)) ew.update(null, message);
        }
        notifyAll();
    }

    public synchronized void forwardToMapView(ModelViewMessage message) {
        mapView.update(null, message);
        notifyAll();
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
     * is used by the server to send string to the client with the rmi connection
     * @param string
     */
    public void printFromController(String string) {
        clientView.printFromController(string);
    }


    public void unreferenced() throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(this, true);
    }

    public abstract void closeAll();

    public void handleCharacterMessage(List<Character> character) {
        clientView.setPossibleCharacter(character);
    }
}
