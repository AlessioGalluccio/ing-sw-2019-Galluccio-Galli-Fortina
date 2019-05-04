package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.EnemyView;
import it.polimi.se2019.view.PlayerView;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer implements ServerInterface{

    private PlayerView playerView;
    private ClientInterface skeleton;

    public RMIServer() {

    }


    /*
    send() is used by the RMIClient to send messages with rmi network
     */

    public void send (ViewControllerMessage message){

        playerView.send(message);
    }

    /*
    printFromController() is used by the server to send string to the RMIClient
     */


    public void printFromController(String string) {


            skeleton.printFromController(string);

    }


    /*
    startServer() is used to create the rmi connection between Client and Server
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


    /*
    with sendMap() server send a copy of the map to the client to let the user see all the changes in the map
     */

    public void sendMap(Map map){

        skeleton.sendMap(map);
    }




    public void sendPlayer (Player player){

        skeleton.sendPlayerCopy(player);
    }


    /*
    sendPossibleTarget() send possible target to client using rmi connection
     */

    public void sendPossibleTarget (ArrayList<Target> possibleTarget){

        skeleton.getPossibleTarget(possibleTarget);

    }

    /*
    sendPossibleCharacter() send possible character to client using rmi connection
     */


    public void sendPossibleCharacter (ArrayList<Character> possibleCharacter){

        skeleton.getPossibleCharacter(possibleCharacter);
    }






}






