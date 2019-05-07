package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ClientInterface extends Remote {

    public void send (ViewControllerMessage message);

    public void sendMap(Map map);

    public void printFromController(String string);

    public void getPossibleTarget(ArrayList <Target> possibleTarget);

    public void sendPlayerCopy (Player playerCopy);

    public void getPossibleCharacter (ArrayList<Character> possibleCharacter);

}
