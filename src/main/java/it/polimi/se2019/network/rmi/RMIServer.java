package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.deck.FireMode;
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
import java.util.List;

public class RMIServer implements ServerInterface{

    private PlayerView playerView;



    public void send (ViewControllerMessage message){
            playerView.send(message);
    }


    public void printFromController(String string) {

        try {
            Registry registry = LocateRegistry.getRegistry("client");
            ClientInterface skeleton = (ClientInterface) registry.lookup("client");
            skeleton.printFromController(string);
        } catch (Exception e) {

        }
    }


    public static void main(String args[]) {

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




    }








}





