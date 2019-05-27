package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

public class MustRespawnControllerState implements StateController {
    private Player player;
    private PlayerView playerView;
    private Controller controller;
    private GameHandler gameHandler;

    //TODO sistema questa stringa scrivendo qualcosa di più sensato
    private static String MUST_RESPAWN = "Please, do a respawn";

    public MustRespawnControllerState(Controller controller, GameHandler gameHandler) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        this.controller = controller;
        this.gameHandler = gameHandler;
        this.player = controller.getPlayer();
        this.playerView = controller.getPlayerView();
    }

    @Override
    public void handle(ViewControllerMessage arg) {
        arg.handle(this);
    }

    @Override
    public void handleAction(int actionID) {
        youCantDoThis();
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
        youCantDoThis();
    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        youCantDoThis();
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, ColorRYB colorAmmo) {
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

    private void youCantDoThis(){
        playerView.printFromController(MUST_RESPAWN);
        controller.removeLastReceivedMessage();
    }
}
