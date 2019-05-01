package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.EnemyView;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {


    public RMIClient() {
    }

    public void send (ViewControllerMessage message){
        try {
        Registry registry = LocateRegistry.getRegistry("server");
        ServerInterface stub = (ServerInterface) registry.lookup("Server");
        stub.send(message);
        } catch (Exception e) {

        }

    }






    /*
    the following methods will be used by the RMIServer
     */


    public Map sendMap(Map map) {

        return map;
    }


    public EnemyView sendEnemy(EnemyView enemy){

        return enemy;
    }


    public Player sendPlayer (Player player){

        return player;
    }





}

