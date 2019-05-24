package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;

public class NotYourTurnState implements StateController {

    private Player player;
    private PlayerView playerView;
    private Controller controller;
    private GameHandler gameHandler;
    private final String NOT_YOUR_TURN_RESPONSE = "Please, wait your turn";

    NotYourTurnState(Controller controller, GameHandler gameHandler) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        this.controller = controller;
        this.gameHandler = gameHandler;
        controller.setNumOfActionTaken(0);
    }

    @Override
    public void handleAction(int actionID) {
        cantDoThisHandler();
    }

    @Override
    public void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        cantDoThisHandler();
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        cantDoThisHandler();

    }

    @Override
    public void handleFiremode(int firemodeID) {
        cantDoThisHandler();

    }

    @Override
    public void handleLogin(String playerNickname, Character chosenCharacter) {
        //TODO controlla
        cantDoThisHandler();
    }


    @Override
    public void handleNewton(NewtonCard usedCard) {
        cantDoThisHandler();

    }

    @Override
    public void handleNope() {
        cantDoThisHandler();

    }

    @Override
    public void handleOptional(int numOptional) {
        cantDoThisHandler();
    }

    @Override
    public void handlePlayer(int playerID) {
        cantDoThisHandler();

    }

    @Override
    public void handleReload(int weaponID) {
        cantDoThisHandler();
    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        //TODO implementami! Sono diverso!

    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, ColorRYB colorAmmo) {
        cantDoThisHandler();

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        cantDoThisHandler();
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        cantDoThisHandler();
    }

    @Override
    public void handle(ViewControllerMessage arg) {

        //controlls if it's the turn of the player. If it is, it changes the state and it passes the message to the new state
        int IDPlayer = arg.getAuthorID();
        if(IDPlayer == gameHandler.getTurnPlayerID()) {
            StateController nextState = new EmptyControllerState(controller, gameHandler);
            nextState.handle(arg);
            controller.setState(nextState);
        }

        else {
            controller.addMessageListReceived(arg);
            arg.handle(this);
        }

    }

    private void cantDoThisHandler(){
        controller.getLastReceivedMessage().getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);
        controller.removeLastReceivedMessage();
    }

}
