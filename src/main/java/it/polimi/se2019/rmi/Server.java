package it.polimi.se2019.rmi;

import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.view.PlayerView;



import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerInterface{

    private PlayerView playerView;
    private Character choosenCharacter;


    @Override
    public void chooseAction(String choosenAction) throws RemoteException {
        switch (choosenAction){
            case "move" :
                playerView.createActionMessage(Identificator.MOVE);
                break;

            case "grab" :
                playerView.createActionMessage(Identificator.GRAB);
                break;

            case "shoot" :
                playerView.createActionMessage(Identificator.SHOOT);
                break;
        }
    }

    @Override
    public void chooseCharacter(String choosenCharacter) throws RemoteException {

    }

    @Override
    public void chooseTarget(String choosenTarget) throws RemoteException {

    }

    @Override
    public void login(String nickname, String character) throws RemoteException {
        switch (character) {
            case "VIOLET" :
                choosenCharacter = new Character(character, "violet" );
                break;

            case ":D-STRUCT-OR":
                choosenCharacter = new Character(character, "yellow");
                break;

            case "BANSHEE":
                choosenCharacter = new Character(character, "blue");
                break;

            case "DOZEN":
                choosenCharacter = new Character(character, "grey");
                break;

            case "SPROG":
                choosenCharacter = new Character(character, "green");
                break;

        }
        playerView.createLoginMessage(Identificator.LOGIN_MESSAGE, nickname, choosenCharacter);

    }









    public static void main(String args[]) {

        try {
            Server obj = new Server();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}






