package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.EnemyView;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements ClientInterface{

    private ServerInterface stub;

    private ClientView clientView;



    public RMIClient() {
    }



    /*
    startClient() is used to create the connection between Client and Server
     */

    public static void startClient() {

        /*
        RMIClient is registered in a registry so it can be use as a server by the RMIServer class to send model's
        changes or strings.
         */
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


        /*
        client is connected with the server and can send messages to the RMIServer class
         */


        try {
            Registry registry = LocateRegistry.getRegistry("server");
            ServerInterface stub = (ServerInterface) registry.lookup("server");

        } catch (Exception e) {

        }

    }

    /*
    send() method is used to send messages to the RMIServer.
     */


    public void send (ViewControllerMessage message){
        try {
            stub.send(message);
        } catch (Exception e) {

        }

    }

    /*
    printFromController() is used by the server to send string to the client with the rmi connection
     */


    public void printFromController(String string) {

        clientView.printFromController(string);

    }








    /*
    the following methods will be used by the RMIServer
     */


    public Map sendMap(Map map) {

        return map;
    }



    public Player sendPlayer (Player player){

        return player;
    }





}

