package it.polimi.se2019.ui;

import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.SkullBoardView;

public interface UiInterface {

    void login(boolean success, boolean isFirst);

    void selectedMap(int choosMap);

    void startGame();

    void disconnect(int matchID);

    void setSkullBoard(SkullBoardView skullBoardView);

    void setMapView(MapView mapView);

    void setEnemyView(EnemyView enemyView);

    //TODO aggiungere metodo per gestire printFromController

    //TODO aggiungere metodi per stampare mappa, player, enemy etc...

}
