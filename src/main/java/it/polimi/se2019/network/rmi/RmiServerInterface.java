package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.Target;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RmiServerInterface extends Remote {
    void connect(RmiClientInterface client) throws RemoteException;
}
