package it.polimi.se2019.rmi;

import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.view.PlayerView;

import java.rmi.RemoteException;

public class Server implements ServerInterface{

    private PlayerView playerView;
    private Character choosenCharacter;


    @Override
    public void chooseAction(String choosenAction) throws RemoteException {
        switch (choosenAction){
            case "move" :
                playerView.createActionMessage(1);
                break;

            case "grab" :
                playerView.createActionMessage(2);
                break;

            case "shoot" :
                playerView.createActionMessage(3);
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
}
