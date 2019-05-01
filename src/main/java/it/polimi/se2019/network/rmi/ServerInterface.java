package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {



    public void send (ViewControllerMessage message);





}
