package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.view.ViewControllerMess.*;

public interface StateController {

    /**
     * used by the controller to pass the new message, the other methods are used by messages themselves
     * @param arg
     */
    public void handle(ViewControllerMessage arg);

    public void handleAction(int actionID);

    public void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded);

    public void handleCell(int coordinateX, int coordinateY);

    public void handleFiremode(int firemodeID);

    public void handleLogin(String playerNickname, Character chosenCharacter);

    public void handleNewton(NewtonCard usedCard);

    public void handleNope();

    public void handleOptional(int numOptional);

    public void handlePlayer(int playerID);

    public void handleReload(int weaponID);

    public void handleTagback(TagbackGranedCard usedCard);

    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost);

    public void handleTeleporter(TeleporterCard usedCard);

    public void handleWeaponCard(WeaponCard usedCard);

    public void handlePassTurn();

    public void handleFire();

    public void handleReconnection(boolean isConnected);

}
