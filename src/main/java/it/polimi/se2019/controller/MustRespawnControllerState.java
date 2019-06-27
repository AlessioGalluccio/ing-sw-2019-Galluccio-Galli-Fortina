package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

public class MustRespawnControllerState extends StateController {
    private Player playerAuthor;
    private String errorString;
    private String stringToPlayerView;
    boolean completedRespawn = false;


    public static final String POWERUP_DISCARD_REQUEST = "Please, discard a PowerUp to spawn";
    public static final String TOO_MANY_CARDS = "You have already three PowerUps. ";

    /**
     * constructor
     * @param controller the controller of the player
     * @param gameHandler the gamehandler of the match
     */
    public MustRespawnControllerState(Controller controller, GameHandler gameHandler) {
        super(controller, gameHandler);
        this.playerAuthor = controller.getAuthor();
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
    public void handleTagback(TagbackGrenadeCard usedCard) {
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
    public void handleDiscardPowerup(int powerupID) {

        errorString = respawnPlayerWithPowerup(controller.getAuthor(), powerupID);
        //if it's null, there are no errors. If it is, we don't change the state and we wait another message
        //we don't do addReceived for this reason. We wait for a DiscardPowerupMessage
        if (errorString == null) {
            //the NotYourTurnState will do the gamehandler.nextTurn()
            gameHandler.removeJustDied(playerAuthor);
            controller.setState(new NotYourTurnState(controller, gameHandler, true));
            completedRespawn = true;

        }
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        youMustRespawn();
    }

    @Override
    //this will handle the spawn even if there's a disconnection in the middle
    public void handleReconnection(boolean isConnected) {
        //it's disconnected AND the state is this (it hasn't changed in the meantime
        if(!isConnected && controller.getState() == this){
            gameHandler.setPlayerConnectionStatus(controller.getAuthor(), false);
            //this is different from standard method
            gameHandler.randomRespawnNotConnectedPlayer(playerAuthor);
            //since this stae add a powerup, we must discard it
            try {
                playerAuthor.discardCard(playerAuthor.getPowerupCardList().get(0), true);
            } catch (NotPresentException e) {
                //shouldn't happen
            }
            controller.setState(new DisconnectedControllerState(controller, gameHandler));
            completedRespawn = true;
        }
    }

    @Override
    public String handle(ViewControllerMessage arg) {
        arg.handle(this);
        if(completedRespawn){
            stringToPlayerView = null;
        }
        else if(errorString != null){
            stringToPlayerView = errorString + POWERUP_DISCARD_REQUEST;
            errorString = null;
        }
        else{
            stringToPlayerView = POWERUP_DISCARD_REQUEST ;
        }
        return stringToPlayerView;
    }

    /**
     * handles the invalid messages
     */
    private void youMustRespawn(){
        stringToPlayerView = POWERUP_DISCARD_REQUEST;
    }
}
