package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.EnemyView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    private static ClientView clientView;

    public RMIClient() {
    }

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            ServerInterface stub = (ServerInterface) registry.lookup("Server");
            String response = clientView.send();
            switch (response){

                case "character":
                    stub.chooseCharacter(response);
                    break;

                case "action":
                    stub.chooseAction(response);

                    /* TODO
                     * implementare tutti i possibili case
                     *
                    */
            }

            //String response = stub.sayHello();
            //System.out.println("response: " + response);
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

