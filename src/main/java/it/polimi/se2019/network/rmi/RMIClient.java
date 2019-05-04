package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.ClientViewMap;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIClient implements ClientInterface{

    private ServerInterface stub;

    private ClientView clientView;

    private ClientViewMap clientViewMap;



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
    sendMap() is used by the serve with rmi connection to send the map copy
     */


    public void sendMap(Map map) {

        clientViewMap.setMapCopy(map);
    }




    /*
    getPossibleTarget() is used by RMIServer to send an Arraylist of possible target
     */


    public void getPossibleTarget( ArrayList <Target> possibleTarget){

        clientView.setPossibleTarget(possibleTarget);
    }


    public void sendPlayerCopy (Player playerCopy){

        clientView.updatePlayerCopy(playerCopy);

    }


    public void getPossibleCharacter (ArrayList<Character> possibleCharacter){

        clientView.setPossibleCharacter(possibleCharacter);
    }




}

