package it.polimi.se2019.rmi;

import it.polimi.se2019.model.deck.Target;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {



    public void chooseCharacter(String choosenCharacter) throws RemoteException;
    public void chooseTarget(String choosenTarget ) throws RemoteException;
    public void login (String nickname, String Character ) throws RemoteException;
    public void chooseAction (String choosenAction) throws RemoteException;
}
