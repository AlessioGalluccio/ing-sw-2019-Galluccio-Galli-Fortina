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
    private String errorString;
    private String stringToPlayerView;

    //they are public for tests
    public static final String CANT_DO = "You have already reloaded. ";
    public static final String RELOAD_OR_PASS = "Please, select reload or pass your turn. ";
    public static final String NOT_PRESENT_WEAPON_RELOAD = "Player doesn't have this Weapon. ";
    public static final String WEAPON_LOADED_RELOAD = "This weapon is already loaded. ";
    public static final String NOT_ENOUGH_AMMO_RELOAD = "To reload this weapon, you need more ammo. Discard correct PowerUp cards and try again. ";

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
            errorString = NOT_PRESENT_WEAPON_RELOAD;
            controller.removeReceived();

        }catch(WeaponIsLoadedException e){
            errorString = WEAPON_LOADED_RELOAD;
            controller.removeReceived();


        }catch(NotEnoughAmmoException e){
            errorString = NOT_ENOUGH_AMMO_RELOAD;
            controller.removeReceived();
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
        youCantDoThis();
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
    public String handle(ViewControllerMessage arg) {
        errorString = null;

        arg.handle(this);

        if(errorString != null){
            stringToPlayerView = errorString + RELOAD_OR_PASS;
        }
        else{
            stringToPlayerView = RELOAD_OR_PASS;
        }
        return stringToPlayerView;
    }

    private void youCantDoThis(){
        errorString = CANT_DO;
        controller.removeReceived();
    }
}
