package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;

public class NotYourTurnState extends StateController {

    private Player player;
    private PlayerView playerView;
    private String errorString;
    private String stringToPlayerView;

    private final String NOT_YOUR_TURN_RESPONSE = "Please, wait your turn";

    public NotYourTurnState(Controller controller, GameHandler gameHandler) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        super(controller, gameHandler);
        controller.setNumOfActionTaken(0);
        this.player = controller.getAuthor();
        this.playerView = controller.getPlayerView();
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
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
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
        errorString = null;
        //controlls if it's the turn of the player. If it is, it changes the state and it passes the message to the new state
        int IDPlayer = arg.getAuthorID();
        if(IDPlayer == gameHandler.getTurnPlayerID()) {
            StateController nextState = new EmptyControllerState(controller, gameHandler);
            nextState.handle(arg);
            controller.setState(nextState);
        }

        else {
            controller.addReceived();
            arg.handle(this);
        }

        if(errorString != null){
            stringToPlayerView = errorString;
        }
        else {
            stringToPlayerView = NOT_YOUR_TURN_RESPONSE;
        }
        return stringToPlayerView;

    }

    @Override
    public void endAction() {
        //do nothing, shouldn't be called in this state
    }

    private void cantDoThisHandler(){
        //controller.removeReceived();
    }

}
