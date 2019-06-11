package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

public class HasReloadedControllerState implements StateController {
    private Player player;
    private PlayerView playerView;
    private Controller controller;
    private GameHandler gameHandler;

    private final String CANT_DO = "You have already reloaded. You can only reload or pass your turn";
    private final String RELOAD_OR_PASS = "Please, select reload or pass your turn";
    private final String NOT_PRESENT_WEAPON_RELOAD = "Error: Player doesn't have this Weapon";
    private final String WEAPON_LOADED_RELOAD = "This weapon is already loaded";
    private final String NOT_ENOUGH_AMMO_RELOAD = "To reload this weapon, you need more ammo. Discard correct PowerUp cards and try again";

    public HasReloadedControllerState(Controller controller, GameHandler gameHandler){
        this.controller = controller;
        this.gameHandler = gameHandler;
        this.player = controller.getAuthor();
        this.playerView = controller.getPlayerView();
    }



    @Override
    public void handleAction(int actionID) {
        youCantDoThis();
    }

    @Override
    public void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        youCantDoThis();
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        youCantDoThis();
    }

    @Override
    public void handleFiremode(int firemodeID) {
        youCantDoThis();
    }

    @Override
    public void handleLogin(String playerNickname, Character chosenCharacter) {
        youCantDoThis();
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        youCantDoThis();
    }

    @Override
    public void handleNope() {
        youCantDoThis();
    }

    @Override
    public void handleOptional(int numOptional) {
        youCantDoThis();
    }

    @Override
    public void handlePlayer(int playerID) {
        youCantDoThis();
    }

    @Override
    public void handleReload(int weaponID) {
        try{
            player.loadWeapon(weaponID);
        }catch(NotPresentException e){
            playerView.printFromController(NOT_PRESENT_WEAPON_RELOAD);
            playerView.printFromController(RELOAD_OR_PASS);
            controller.removeLastReceivedMessage();
        }catch(WeaponIsLoadedException e){
            playerView.printFromController(WEAPON_LOADED_RELOAD);
            playerView.printFromController(RELOAD_OR_PASS);
            controller.removeLastReceivedMessage();
        }catch(NotEnoughAmmoException e){
            playerView.printFromController(NOT_ENOUGH_AMMO_RELOAD);
            playerView.printFromController(RELOAD_OR_PASS);
            controller.removeLastReceivedMessage();
        }
    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        youCantDoThis();
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        youCantDoThis();
    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        youCantDoThis();
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        youCantDoThis();
    }

    @Override
    public void handlePassTurn() {
        //TODO
    }

    @Override
    public void handleFire() {
        //TODO
    }

    @Override
    public void handleReconnection(boolean isConnected) {
        //TODO controlla
        if(!isConnected){
            gameHandler.setPlayerConnectionStatus(player, false);
            gameHandler.nextTurn();
            controller.setState(new DisconnectedControllerState(controller, gameHandler));
        }
    }

    @Override
    public void handle(ViewControllerMessage arg) {
        arg.handle(this);
    }

    private void youCantDoThis(){
        playerView.printFromController(CANT_DO);
        controller.removeLastReceivedMessage();
    }
}
