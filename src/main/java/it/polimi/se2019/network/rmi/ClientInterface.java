package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.EnemyView;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.rmi.Remote;

public interface ClientInterface extends Remote {

    public void send (ViewControllerMessage message);


    public Map sendMap(Map map);


    public EnemyView sendEnemy(EnemyView enemy);


    public Player sendPlayer (Player player);


    public void printFromController(String string);

}
