package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

public class MustRespawnControllerState extends StateController {
    private Player player;
    private PlayerView playerView;
    private String errorString;
    private String stringToPlayerView;


    //TODO sistema questa stringa scrivendo qualcosa di più sensato
    private static String MUST_RESPAWN = "Please, do a respawn";

    public MustRespawnControllerState(Controller controller, GameHandler gameHandler) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        super(controller, gameHandler);
        this.player = controller.getAuthor();
        this.playerView = controller.getPlayerView();
    }


    @Override
    public void handleAction(int actionID) {
        youMustRespawn();
    }

    @Override
    public void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        //TODO fai spawn
        gameHandler.nextTurn();   //TODO potrebbe creare
                                    //     problemi di deadlock, nel caso in cui gamehandler cambi lo stato prima di
                                    //     questo
        controller.setState(new NotYourTurnState(controller, gameHandler));
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        youMustRespawn();
    }

    @Override
    public void handleFiremode(int firemodeID) {
        youMustRespawn();
    }

    @Override
    public void handleLogin(String playerNickname, Character chosenCharacter) {
        youMustRespawn();
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        youMustRespawn();
    }

    @Override
    public void handleNope() {
        youMustRespawn();
    }

    @Override
    public void handleOptional(int numOptional) {
        youMustRespawn();
    }

    @Override
    public void handlePlayer(int playerID) {
        youMustRespawn();
    }

    @Override
    public void handleReload(int weaponID) {
        youMustRespawn();
    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        youMustRespawn();
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        youMustRespawn();
    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        youMustRespawn();
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        youMustRespawn();
    }

    @Override
    public void handlePassTurn() {
        youMustRespawn();
    }

    @Override
    public void handleFire() {
        youMustRespawn();
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
    public void handleDiscardPowerup(int powerupID) {
        //TODO
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        //TODO
    }


    @Override
    public String handle(ViewControllerMessage arg) {
        arg.handle(this);
        stringToPlayerView = MUST_RESPAWN;
        return stringToPlayerView;
    }

    @Override
    public void endAction() {
        //do nothing, shouldn't be called in this state
    }

    private void youMustRespawn(){
    }
}
