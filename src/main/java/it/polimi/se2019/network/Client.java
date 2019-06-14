package it.polimi.se2019.network;

import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;
import it.polimi.se2019.view.clientView.ClientEnemyView;
import it.polimi.se2019.view.clientView.ClientMapView;
import it.polimi.se2019.view.clientView.ClientSkullBoardView;
import it.polimi.se2019.view.clientView.ClientView;

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

    protected Client() throws RemoteException {
        super();
    }

    /**
     * Connect client to server
     */
    public abstract void connect();

    public void handleLoginMessage(boolean success, boolean isFirst) {
        clientView.handleLogin(success, isFirst);
    }

    public synchronized void forwardToClientView(ModelViewMessage message) {
        //If views are not initialized wait
        while(mapView.getMapCopy()==null &&
                clientView.getPlayerCopy()==null &&
                enemyViews.get(0).getNickname()==null &&
                enemyViews.get(1).getNickname()==null &&
                enemyViews.get(2).getNickname()==null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        clientView.update(null, message);
    }

    public void handleEnemyMessage(String nickname) {
        enemyViews.add(new ClientEnemyView(nickname));
    }

    public void handleDisconnection() {
        clientView.handleDisconnection();
    }

    public void forwardToEnemyView(ModelViewMessage message) {
    }

    public void forwardToMapView(ModelViewMessage message) {
    }

    public void forwardToSkullBoardView(ModelViewMessage message) {

    }

    public void setUpUi(UiInterface ui) {
        skullBoardView = new ClientSkullBoardView(ui);
        for(ClientEnemyView ew : enemyViews) {
            ew.setUi(ui);
        }
        mapView = new ClientMapView(ui);
    }

    /**
     * is used by the server to send string to the client with the rmi connection
     * @param string
     */
    public void printFromController(String string) {
        clientView.printFromController(string);
    }

}
