package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.view.ViewControllerMess.*;

public abstract class StateController {

    /**
     * used by the controller to pass the new message, the other methods are used by messages themselves
     * @param arg
     */
    public abstract String handle(ViewControllerMessage arg);

    public abstract void handleAction(int actionID);

    public abstract void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded);

    public abstract void handleCell(int coordinateX, int coordinateY);

    public abstract void handleFiremode(int firemodeID);

    public abstract void handleLogin(String playerNickname, Character chosenCharacter);

    public abstract void handleNewton(NewtonCard usedCard);

    public abstract void handleNope();

    public abstract void handleOptional(int numOptional);

    public abstract void handlePlayer(int playerID);

    public abstract void handleReload(int weaponID);

    public abstract void handleTagback(TagbackGranedCard usedCard);

    public abstract void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost);

    public abstract void handleTeleporter(TeleporterCard usedCard);

    public abstract void handleWeaponCard(WeaponCard usedCard);

    public abstract void handlePassTurn();

    public abstract void handleFire();

    public abstract void handleReconnection(boolean isConnected);

    public abstract void endAction();

    public abstract void handleDiscardPowerup(int powerupID);

    public abstract void handleDiscardWeapon(int weaponID);

}
