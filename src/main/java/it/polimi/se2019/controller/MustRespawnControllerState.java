package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

public class MustRespawnControllerState extends StateController {
    private Player playerAuthor;
    private PlayerView playerView;
    private String errorString;
    private String stringToPlayerView;


    //TODO sistema questa stringa scrivendo qualcosa di più sensato
    public static final String POWERUP_DISCARD_REQUEST = "Please, discard a Powerup to spawn";
    public static final String TOO_MANY_CARDS = "The player has already three cards. ";

    public MustRespawnControllerState(Controller controller, GameHandler gameHandler) {
        //TODO aggiungere playerAuthor e playerView (anche a tutti gli stati!)
        super(controller, gameHandler);
        this.playerAuthor = controller.getAuthor();
        this.playerView = controller.getPlayerView();
        //playerAuthor picks up one powerup cards
        PowerupDeck deck = gameHandler.getPowerupDeck();
        try{
            playerAuthor.addPowerupCard(deck.pick());
        }catch (TooManyException e){
            errorString = TOO_MANY_CARDS;
        }

        //we immediately send a message to the player
        if(errorString == null){
            controller.getPlayerView().printFromController(POWERUP_DISCARD_REQUEST);
        }
        else {
            controller.getPlayerView().printFromController(errorString + POWERUP_DISCARD_REQUEST);
            errorString = null;
        }

    }


    @Override
    public void handleAction(int actionID) {
        youMustRespawn();
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
            gameHandler.setPlayerConnectionStatus(playerAuthor, false);
            gameHandler.nextTurn();
            controller.setState(new DisconnectedControllerState(controller, gameHandler));
        }
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {

        errorString = respawnPlayerWithPowerup(controller.getAuthor(), powerupID);
        //if it's null, there are no errors. If it is, we don't change the state and we wait another message
        //we don't do addReceived for this reason. We wait for a DiscardPowerupMessage
        if (errorString != null) {
            //the NotYourTurnState will do the gamehandler.nextTurn()
            controller.setState(new NotYourTurnState(controller, gameHandler, true));

        }
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        youMustRespawn();
    }


    @Override
    public String handle(ViewControllerMessage arg) {
        arg.handle(this);
        if(errorString != null){
            stringToPlayerView = errorString + POWERUP_DISCARD_REQUEST;
            errorString = null;
        }
        else{
            stringToPlayerView = controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
        }
        return stringToPlayerView;
    }

    @Override
    public void endAction() {
        //do nothing, shouldn't be called in this state
    }

    private void youMustRespawn(){
        stringToPlayerView = POWERUP_DISCARD_REQUEST;
    }
}
