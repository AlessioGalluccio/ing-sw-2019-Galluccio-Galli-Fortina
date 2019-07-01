package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;


public class ActionSelectedControllerState extends StateController {

    private Player playerAuthor;
    private Action action;
    private String errorString;
    private String stringToPlayerView;
    private boolean hasShoot = false;
    private boolean skipSelected = false;

    /**
     * constructor
     * @param controller the Controller of the player
     * @param gameHandler the GameHandler of the match
     * @param action the action selected
     */
    public ActionSelectedControllerState(Controller controller, GameHandler gameHandler, Action action) {
        super(controller, gameHandler);
        this.playerAuthor = controller.getAuthor();
        this.action = action;
        this.controller.resetMessages();
        this.controller.addMessageListExpected(action.getStringAndMessageExpected());
        this.controller.getPlayerView().printFromController(controller.getCopyMessageListExpected().get(0).getString());
    }

    @Override
    public void handleAction(int actionID) {
        //do nothing, should not arrive here
    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        try{
            action.addCell(coordinateX, coordinateY);
            controller.addReceived();
        }catch(WrongInputException e){
            errorString = e.getMessage();
        }
    }

    @Override
    public void handleFiremode(int firemodeID) {
        try{
            action.addFireMode(firemodeID);
            controller.addReceived();
        }catch (WrongInputException e){
            errorString = e.getMessage();
        }
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        //do nothing, shouldn't arrive here
    }

    @Override
    public void handleNope() {
        try {
            playerAuthor.payAmmoCost(action.getCost());
            this.skipSelected = true;
        } catch (NotEnoughAmmoException e) {
            //shouldn't happen
            this.skipSelected = true;
        }

    }

    @Override
    public void handlePlayer(int playerID) {
        try{
            if(gameHandler.getPlayerByID(playerID).getCell() != null){
                action.addPlayerTarget(playerID);
                controller.addReceived();
            }
            else{
                errorString = PLAYER_NOT_PRESENT;
            }

        }catch (WrongInputException e){
            errorString = e.getMessage();
        }

    }

    @Override
    public void handleOptional(int numOptional) {
        try{
            action.addOptional(numOptional);
        }catch (WrongInputException e){
            errorString = e.getMessage();
        }catch (NotEnoughAmmoException e){
            errorString = NOT_ENOUGH;
        }
    }

    @Override
    public void handleReload(int weaponID) {
        try {
            action.addReload(weaponID);
            //it's very rare that there's the StringAndMessage
            if(controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getMessageID()
                    == Identificator.RELOAD_MESSAGE){
                controller.addReceived();
            }
        } catch (WrongInputException e) {
            errorString = e.getMessage();

        } catch (NotPresentException e) {
            errorString = WEAPON_NOT_PRESENT;
        } catch (NotEnoughAmmoException e) {
            errorString = NOT_ENOUGH;
        } catch (WeaponIsLoadedException e) {
            errorString = WEAPON_LOADED_RELOAD;
        }

    }

    @Override
    public void handleTagback(TagbackGrenadeCard usedCard) {
        stringToPlayerView = CANT_DO_THIS;
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        try{
            action.addTargetingScope(usedCard.getID(), cost);
            //no addReceived, because no StringAndMessage

        }catch(WrongInputException e){
            errorString = e.getMessage();

        }catch(NotEnoughAmmoException e){
            errorString = NOT_ENOUGH;

        }catch(NotPresentException e){
            errorString = CARD_NOT_PRESENT;

        }catch (FiremodeOfOnlyMarksException e){
            errorString = ONLY_MARKS;
        }

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        try{
            action.addWeapon(usedCard);
            controller.addReceived();
        }catch(WrongInputException e){
            errorString = e.getMessage();
        }
    }

    @Override
    public void handlePassTurn() {
        errorString = CANT_DO_THIS;
    }

    @Override
    public void handleFire() {
        try {
            action.fire();
            controller.addReceived();
            this.hasShoot = true; //even if I didn't finished the sequence but I could shoot (optional targets), I will end the action  in this way
        }catch(WrongInputException e){
            errorString = e.getMessage();
        }
    }

    @Override
    public void handleReconnection(boolean isConnected) {
        //I need to overload because the player must pay the cost of the action
        super.handleReconnection(isConnected);
        if(!isConnected){
            try {
                playerAuthor.payAmmoCost(action.getCost());
            } catch (NotEnoughAmmoException e) {
                //shouldn't happen
            }
        }
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {
        try{
            playerAuthor.discardCard(gameHandler.getPowerupCardByID(powerupID), false);
        }catch (NotPresentException e){
            errorString = POWERUP_NOT_PRESENT_DISCARD;
        }

    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        try{
            action.addDiscardWeapon(gameHandler.getWeaponCardByID(weaponID));
            //it's a rare case that there's the StringAndMessage, or it should never happen?
            if(controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getMessageID()
                    == Identificator.DISCARD_WEAPON_MESSAGE){
                controller.addReceived();
            }
        }catch(WrongInputException e){
            errorString = e.getMessage();
        }
    }

    @Override
    public String handle(ViewControllerMessage arg) {
        if(startingHandler(arg)){
            arg.handle(this);
            endingHandler();
        }
        return stringToPlayerView;
    }

    /**
     * it adds the message to messageListReceived and it adds indexExpected if it's the correct message
     * if it's not correct, it prints a error message in the Player view
     * @param arg the message
     * @return      1 if it's the message expected for the action
     *              0 if not (message is discarded)
     */
    private boolean startingHandler(ViewControllerMessage arg) {
        int index = controller.getIndexExpected();
        int expectedID = controller.getCopyMessageListExpected().get(index).getMessageID();
        int messageID = arg.getMessageID();
        if(messageID == expectedID || messageID == Identificator.TARGETING_SCOPE_MESSAGE || messageID == Identificator.NOPE_MESSAGE
                || messageID == Identificator.OPTIONAL_MESSAGE || messageID == Identificator.RELOAD_MESSAGE
                || messageID == Identificator.PASS_MESSAGE || messageID == Identificator.FIRE_MESSAGE || messageID == Identificator.CONNECTION_MESSAGE) {
            return true;
        }
        else {
            stringToPlayerView = CANT_DO_THIS + controller.getCopyMessageListExpected().get(index).getString();
            return false;
        }
    }

    /**
     * It creates the message to the view after the message. If the sequence of message is ended,
     * it sends them to the model and it changes the State of the controller
     */
    private void endingHandler() {
        //it controls if the sequence of action is completed
        if(controller.getIndexExpected() < controller.getCopyMessageListExpected().size() && !hasShoot && !skipSelected){
            //The sequence is not completed. It prints the next request
            if(errorString != null){
                stringToPlayerView = errorString + controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
            }
            else{
                stringToPlayerView = controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
            }
            //error string is reset to null
            errorString = null;
        }
        else{
            //the sequence is completed. It goes to the EmptyState because it could use powerus or recharge weapons
            controller.addNumOfActionTaken();
            controller.setState(new EmptyControllerState(controller, gameHandler));
            stringToPlayerView = null;
        }
    }
}
