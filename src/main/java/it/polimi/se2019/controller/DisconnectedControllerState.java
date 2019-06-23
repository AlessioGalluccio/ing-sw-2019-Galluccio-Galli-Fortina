package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

public class DisconnectedControllerState extends StateController {
    private Player player;
    private String stringToPlayerView = STILL_DISCONNECTED;

    //this string is for finding errors in testing. Should never appear
    public static final String STILL_DISCONNECTED = "Still disconnecte";


    public DisconnectedControllerState(Controller controller, GameHandler gameHandler) {
        super(controller, gameHandler);
        this.player = controller.getAuthor();
    }

    @Override
    public void handleAction(int actionID) {
        //do nothing
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        //do nothing
    }

    @Override
    public void handleFiremode(int firemodeID) {
        //do nothing
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        //do nothing
    }

    @Override
    public void handleNope() {
        //do nothing
    }

    @Override
    public void handleOptional(int numOptional) {
        //do nothing
    }

    @Override
    public void handlePlayer(int playerID) {
        //do nothing
    }

    @Override
    public void handleReload(int weaponID) {
        //do nothing
    }

    @Override
    public void handleTagback(TagbackGrenadeCard usedCard) {
        //do nothing
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        //do nothing
    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        //do nothing
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        //do nothing
    }

    @Override
    public void handlePassTurn() {
        //do nothing
    }

    @Override
    public void handleFire() {
        //do nothing
    }

    @Override
    public void handleReconnection(boolean isConnected) {
        if(isConnected){
            gameHandler.setPlayerConnectionStatus(player, true);
            controller.setState(new NotYourTurnState(controller, gameHandler, false));
        }
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {
        //do nothing
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        //do nothing
    }


    @Override
    public String handle(ViewControllerMessage arg) {
        arg.handle(this);
        return stringToPlayerView;
    }
}
