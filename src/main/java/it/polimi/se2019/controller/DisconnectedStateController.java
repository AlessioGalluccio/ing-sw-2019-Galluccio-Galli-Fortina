package it.polimi.se2019.controller;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

public class DisconnectedStateController implements StateController {
    @Override
    public void handleAction(int actionID) {

    }

    @Override
    public void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded) {

    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {

    }

    @Override
    public void handleFiremode(int firemodeID) {

    }

    @Override
    public void handleLogin(String playerNickname, Character chosenCharacter) {

    }

    @Override
    public void handleNewton(NewtonCard usedCard) {

    }

    @Override
    public void handleNope() {

    }

    @Override
    public void handleOptional(int numOptional) {

    }

    @Override
    public void handlePlayer(int playerID) {

    }

    @Override
    public void handleReload(int weaponID) {

    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {

    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {

    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {

    }

    @Override
    public void handlePassTurn() {

    }

    @Override
    public void handleFire() {

    }

    @Override
    public void handleReconnection(boolean isConnected) {

    }

    @Override
    public void handle(ViewControllerMessage arg) {
        arg.handle(this);
    }
}
