package it.polimi.se2019.network.rmi;

import it.polimi.se2019.model.deck.FireMode;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {



    public void chooseCharacter(String choosenCharacter) throws RemoteException;
    public void chooseTarget(String choosenTarget ) throws RemoteException;
    public void login (String nickname, String Character ) throws RemoteException;
    public void chooseAction (String choosenAction) throws RemoteException;
    public void reloadWeapon (String weapon) throws RemoteException;
    public void chooseCell (String cell) throws RemoteException;
    public void endTurn (String ending) throws RemoteException;
    public void chooseFireMode (String choosenFireMode ) throws  RemoteException;
    public void chooseNewton (String choosenNewton) throws RemoteException;
    public void Nope (String nope) throws RemoteException;
    public void chooseTagbackGranate (String choosenTagback) throws RemoteException;
    public void chooseTargetingScope (String choosenTargeting) throws RemoteException;
    public void chooseTarger (String choosenTarget) throws RemoteException;
    public void chooseTeleporter (String choosenTeleporter) throws RemoteException;
    public void undo(String undo) throws RemoteException;



}
