package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.ArrayList;

public class FirstTurnState extends StateController {

    private Player playerAuthor;
    private String errorString;
    private String stringToPlayerView;
    private boolean isCharacterSelected = false;

    public static final String CHARACTER_REQUEST = "Please, select a Character";
    public static final String POWERUP_DISCARD_REQUEST = "Please, discard a Powerup to spawn";
    public static final String TOO_MANY_CARDS = "The player has already three cards. ";

    private static final StringAndMessage firstMessage =
            new StringAndMessage(Identificator.CHARACTER_MESSAGE, CHARACTER_REQUEST);
    private static final StringAndMessage secondMessage =
            new StringAndMessage(Identificator.DISCARD_POWERUP_MESSAGE, POWERUP_DISCARD_REQUEST);

    /**
     * constructor of FirstTurnState
     * @param controller the controller of the player
     * @param gameHandler the gamehandler of the game
     */
    public FirstTurnState(Controller controller, GameHandler gameHandler) {
        super(controller, gameHandler);
        if(controller.getAuthor().getCharacter() == null){
            ArrayList<StringAndMessage> listExpectedMessages = new ArrayList<>();
            listExpectedMessages.add(firstMessage);
            listExpectedMessages.add(secondMessage);
            controller.resetMessages();
            controller.addMessageListExpected(listExpectedMessages);
            this.playerAuthor = controller.getAuthor();

            //player picks up two powerup cards
            PowerupDeck deck = gameHandler.getPowerupDeck();
            try{
                playerAuthor.addPowerupCard(deck.pick());
                playerAuthor.addPowerupCard(deck.pick());
            }catch (TooManyException e){
                errorString = TOO_MANY_CARDS;
            }

            //we immediately send a message to the player
            if(errorString == null){
                controller.getPlayerView().printFromController(CHARACTER_REQUEST);
            }
            else {
                controller.getPlayerView().printFromController(errorString + CHARACTER_REQUEST);
                errorString = null;
            }

            //I send the possible targets to the client
            controller.getPlayerView().setPossibleCharacter(gameHandler.possibleCharacter());
        }
        else{
            //the character is already selected. It happens if the player is disconnected after choosing the character,
            // but before discarding the powerup
            controller.resetMessages();
            controller.addMessageListExpected(secondMessage);
            controller.getPlayerView().printFromController(POWERUP_DISCARD_REQUEST);
        }

    }

    @Override
    public void handleAction(int actionID) {
        errorString = CANT_DO_THIS;
    }
    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleFiremode(int firemodeID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleNope() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleOptional(int numOptional) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handlePlayer(int playerID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleReload(int weaponID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleTagback(TagbackGrenadeCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handlePassTurn() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleFire() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {
        if(!isCharacterSelected){
            errorString = CANT_DO_THIS;
        }
        else{
            errorString = respawnPlayerWithPowerup(playerAuthor, powerupID);

            //if it's null, there are no errors. If it is, we don't change the state and we wait another message
            //we don't do addReceived for this reason. We wait for a DiscardPowerupMessage
            if(errorString == null){ //TODO check
                //spawn process is finished. we go to the next state
                controller.setState(new EmptyControllerState(controller, gameHandler));
            }

        }
    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleCharacter(int characterID) {
        playerAuthor.setCharacter(new Character(characterID));
        this.isCharacterSelected = true;
        controller.addReceived();
    }

    @Override
    public String handle(ViewControllerMessage arg) {
        if(arg.getMessageID() ==
                controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getMessageID()){
            arg.handle(this);
        }
        else{
            errorString = CANT_DO_THIS;
        }

        if(errorString != null){
            try{
                stringToPlayerView = errorString +
                        controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
                errorString = null;
            }catch (IndexOutOfBoundsException e){ //it has finished the series of commands, we don't print anything
                stringToPlayerView = null;
            }

        }
        else{
            if(controller.getIndexExpected() < controller.getCopyMessageListExpected().size()){
                stringToPlayerView = controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
            }
            else{
                stringToPlayerView = null; // no other messages
            }

        }
        return stringToPlayerView;
    }
}
