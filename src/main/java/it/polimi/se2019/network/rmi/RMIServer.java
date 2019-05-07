package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer implements ServerInterface{

    private PlayerView playerView;
    private ClientInterface skeleton;

    public RMIServer() {

    }




    /**
     * is used by the RMIClient to send messages with rmi network
     * @param message
     */

    public void send (ViewControllerMessage message){

        playerView.send(message);
    }



    /**
     * is used by the server to send string to the RMIClient
     * @param string
     */


    public void printFromController(String string) {


            skeleton.printFromController(string);

    }




    /**
     * is used to create the rmi connection between Client and Server
     */
    public static void startServer() {

        /*
        RMIServer is registered in a registry so it can be use as a server by the RMIClass class to send messages.
         */


        try {
            RMIServer obj = new RMIServer();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("server", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }


        /*
        server is connected with the client and can send model's changes or strings to the RMIClient class
         */



        try {
            Registry registry = LocateRegistry.getRegistry("client");
            ClientInterface skeleton = (ClientInterface) registry.lookup("client");

        } catch (Exception e) {

        }





    }


    /**
     * server send a copy of the map to the client to let the user see all the changes in the map
     * @param map
     */
    public void sendMap(Map map){

        skeleton.sendMap(map);
    }




    public void sendPlayer (Player player){

        skeleton.sendPlayerCopy(player);
    }



    /**
     * send possible target to client using rmi connection
     * @param possibleTarget
     */

    public void sendPossibleTarget (ArrayList<Target> possibleTarget){

        skeleton.getPossibleTarget(possibleTarget);

    }



    /**
     * send possible character to client using rmi connection
     * @param possibleCharacter
     */


    public void sendPossibleCharacter (ArrayList<Character> possibleCharacter){

        skeleton.getPossibleCharacter(possibleCharacter);
    }






}






