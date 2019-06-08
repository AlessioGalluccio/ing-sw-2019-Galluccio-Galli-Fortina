package it.polimi.se2019.network;

import it.polimi.se2019.ui.UiInterface;
import it.polimi.se2019.view.clientView.ClientEnemyView;
import it.polimi.se2019.view.clientView.ClientMapView;
import it.polimi.se2019.view.clientView.ClientSkullBoardView;
import it.polimi.se2019.view.clientView.ClientView;

import java.util.Observer;
import java.util.List;
import java.util.LinkedList;

public abstract class Client implements Observer {

    protected ClientView clientView;
    private ClientSkullBoardView skullBoardView;
    private List<ClientEnemyView> enemyViews = new LinkedList<>();
    private ClientMapView mapView;

    /**
     * Connect client to server
     */
    public abstract void connect();

    public void handleLoginMessage(boolean success, boolean isFirst) {
        clientView.handleLogin(success, isFirst);
    }

    public void forwardToClientView() {

    }

    public void handleEnemyMessage(String nickname) {
        enemyViews.add(new ClientEnemyView(nickname));
    }

    public void forwardToEnemyView() {

    }

    public void forwardToMapView() {

    }

    public void forwardToSkullBoardView() {

    }

    public void setUpUi(UiInterface ui) {
        skullBoardView = new ClientSkullBoardView(ui);
        for(ClientEnemyView ew : enemyViews) {
            ew.setUi(ui);
        }
        mapView = new ClientMapView(ui);
    }

}
