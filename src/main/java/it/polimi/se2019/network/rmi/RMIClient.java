package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.EnemyView;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements ClientInterface{


    public RMIClient() {
    }

    public void send (ViewControllerMessage message){
        try {
        Registry registry = LocateRegistry.getRegistry("server");
        ServerInterface stub = (ServerInterface) registry.lookup("server");
        stub.send(message);
        } catch (Exception e) {

        }

    }

    public void printFromController(String string) {

    }

    public static void main(String args[]) {

        try {
            RMIClient obj = new RMIClient();
            ClientInterface skeleton = (ClientInterface) UnicastRemoteObject.exportObject(obj, 1);

            // Bind the remote object's skeleton in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("client", skeleton);

            System.err.println("Client ready");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
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

