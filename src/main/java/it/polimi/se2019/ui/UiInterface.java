package it.polimi.se2019.ui;

import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.clientView.ClientEnemyView;
import it.polimi.se2019.view.remoteView.EnemyView;
import it.polimi.se2019.view.remoteView.MapView;
import it.polimi.se2019.view.remoteView.SkullBoardView;

import java.util.List;

public interface UiInterface {

    void login(boolean success, boolean isFirst);

    void selectedMap(int choosMap);

    void startGame();

    void disconnect(int matchID);

    void setSkullBoard(SkullBoardView skullBoardView);

    void setMapView(MapView mapView);

    void setEnemyView(EnemyView enemyView);

    void printFromController(String message);

    void updateCell(Cell cell);

    void updatePlayer();

    void updateEnemy(ClientEnemyView enemyView);

    void updateSkullBoard();

    void printRanking(List<Player> players);

    void turn(); //TODO sceliere parametri


}
