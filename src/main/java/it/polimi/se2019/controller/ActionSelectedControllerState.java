package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;


public class ActionSelectedControllerState extends StateController {

    private Player playerAuthor;
    private PlayerView playerView;
    private Controller controller;
    private GameHandler gameHandler;
    private Action action;
    private static final int FIRST_MESSAGE = 0;
    private String errorString;
    private String stringToPlayerView;



    public ActionSelectedControllerState(Controller controller, GameHandler gameHandler, Action action) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        this.controller = controller;
        this.gameHandler = gameHandler;
        this.playerAuthor = controller.getAuthor();
        this.playerView = controller.getPlayerView();
        this.action = action;
        this.controller.addMessageListExpected(action.getStringAndMessageExpected());
    }


    @Override
    public void handleAction(int actionID) {
        //TODO

    }

    @Override
    public void handleCardSpawn(PowerupCard cardChoosen, PowerupCard cardDiscarded) {
        //TODO

    }

    @Override
    public void handleCell(int coordinateX, int coordinateY) {
        try{
            action.addCell(coordinateX, coordinateY);
        }catch(WrongInputException e){
            errorString = e.getMessage();
            //playerView.printFromController(CELL_WRONG);
            controller.removeReceived();
        }
    }

    @Override
    public void handleFiremode(int firemodeID) {
        try{
            action.addFireMode(firemodeID);
        }catch (WrongInputException e){
            errorString = e.getMessage();
            controller.removeReceived();
        }
        /*
        Player player = gameHandler.getPlayerByID(controller.getLastReceivedMessage().getAuthorID());
        FireMode fireMode = gameHandler.getFireModeByID(firemodeID);
        AmmoBag cost = AmmoBag.createAmmoFromList(fireMode.getCost());

        if(!player.canPayAmmo(cost)){
            controller.getLastReceivedMessage().getAuthorView().printFromController("Not enough ammo for this firemode");
            controller.removeReceived();
        }
        else{
            //TODO sistema
            //controller.sendTargetsToView(controller.getLastReceivedMessage());
        }
        */
    }

    @Override
    public void handleLogin(String playerNickname, Character chosenCharacter) {
        //TODO
    }

    @Override
    public void handleNewton(NewtonCard usedCard) {
        //TODO
        /*
        if(startingHandler(arg)){
            controller.sendTargetsToView(arg);
            endingHandler(arg);
        }
        */
    }

    @Override
    public void handleNope() {
        //TODO
        /*
        int index = controller.getIndexExpected();

        if(!controller.getCopyMessageListExpected().get(index).isOptional()){
            String response = controller.getCopyMessageListExpected().get(index).getString();
            playerView.printFromController(response);
            controller.removeReceived();
        }
        else{
            //do nothing
        }
        */

    }

    @Override
    public void handlePlayer(int playerID) {
        try{
            action.addPlayerTarget(playerID);
        }catch (WrongInputException e){
            errorString = e.getMessage();
            controller.removeReceived();
        }

    }

    @Override
    public void handleOptional(int numOptional) {
        try{
            action.addOptional(numOptional);
        }catch (WrongInputException e){
            //playerView.printFromController(OPTIONAL_WRONG);
            errorString = e.getMessage();
            controller.removeReceived();
        }catch (NotEnoughAmmoException e){
            errorString = NOT_ENOUGH;;
            controller.removeReceived();
        }
    }

    @Override
    public void handleReload(int weaponID) {
        //TODO

        try {
            action.addReload(weaponID);
        } catch (WrongInputException e) {
            errorString = e.getMessage();
            controller.removeReceived();

        } catch (NotPresentException e) {
            errorString = WEAPON_NOT_PRESENT;
            controller.removeReceived();
        } catch (NotEnoughAmmoException e) {
            errorString = NOT_ENOUGH;
            controller.removeReceived();
        } catch (WeaponIsLoadedException e) {
            errorString = WEAPON_LOADED_RELOAD;
            controller.removeReceived();
        }

    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        stringToPlayerView = CANT_DO_THIS;
        controller.removeReceived();
    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        try{
            action.addTargetingScope(usedCard.getID(), cost);

        }catch(WrongInputException e){
            errorString = ALREADY_SELECTED;
            controller.removeReceived();

        }catch(NotEnoughAmmoException e){
            errorString = NOT_ENOUGH;
            controller.removeReceived();

        }catch(NotPresentException e){
            errorString = CARD_NOT_PRESENT;
            controller.removeReceived();

        }catch (FiremodeOfOnlyMarksException e){
            errorString = ONLY_MARKS;
            controller.removeReceived();
        }

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        //TODO niente entra qui dentro. Starting Handler blocca
        errorString = CANT_DO_THIS;
        controller.removeReceived();
    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        try{
            action.addWeapon(usedCard);
        }catch(WrongInputException e){
            errorString = e.getMessage();
        }
    }

    @Override
    public void handlePassTurn() {
        //TODO
    }

    @Override
    public void handleFire() {
        try {
            action.fire();
            stringToPlayerView = controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
        }catch(WrongInputException e){
            controller.removeReceived();
            errorString = e.getMessage();
        }
    }

    @Override
    public void handleReconnection(boolean isConnected) {
        //TODO controlla da sistemare sicuramente
        if(!isConnected){
            gameHandler.setPlayerConnectionStatus(playerAuthor, false);
            gameHandler.nextTurn();
            controller.setState(new DisconnectedControllerState(controller, gameHandler));
        }
    }

    @Override
    public void handleDiscardPowerup(int powerupID) {
        try{
            playerAuthor.discardCard(gameHandler.getPowerupCardByID(powerupID));
        }catch (NotPresentException e){
            errorString = POWERUP_NOT_PRESENT_DISCARD;
        }

    }

    @Override
    public void handleDiscardWeapon(int weaponID) {
        try{
            action.addDiscardWeapon(gameHandler.getWeaponCardByID(weaponID));
        }catch(WrongInputException e){
            errorString = e.getMessage();
            controller.removeReceived();
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
        //TODO sistemare starting a ending handler
        int index = controller.getIndexExpected();
        int expectedID = controller.getCopyMessageListExpected().get(index).getMessageID();
        int messageID = arg.getMessageID();
        if(messageID == expectedID || messageID == Identificator.TARGETING_SCOPE_MESSAGE || messageID == Identificator.NOPE_MESSAGE) {
            controller.addReceived();
            return true;
        }
        else {
            stringToPlayerView = CANT_DO_THIS + controller.getCopyMessageListExpected().get(index).getString();
            return false;
        }
    }


    @Override
    public void endAction() {
        //TODO da controllare pesantemente
        controller.setNumOfActionTaken(controller.getNumOfActionTaken() + 1);
        controller.setState(new EmptyControllerState(controller, gameHandler));
    }

    /**
     * It creates the message to the view after the message. If the sequence of message is ended,
     * it sends them to the model and it changes the State of the controller
     */
    private void endingHandler() {
        //controlls if the sequence of action is completed
        if(controller.getIndexExpected() < controller.getCopyMessageListExpected().size()){
            //The sequence is not completed. It prints the next request
            if(errorString != null){
                stringToPlayerView = errorString + controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
            }
            else{
                stringToPlayerView = controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
            }
            //error string is resetted
            errorString = null;
        }
        else{
            //the sequence is completed. It goes to the EmptyState because it could use powerus or recharge weapons
            controller.setState(new EmptyControllerState(controller, gameHandler));
        }




        /*TODO
        int index = controller.getIndexExpected();
        controller.addReceived(arg);
        controller.setIndexExpected(index + 1);
        if(controller.getCopyMessageListExpected().size() == controller.getIndexExpected()) {
            //Model is modified
            ViewControllerMessage firstMessage = controller.getCopyMessageListReceived().get(FIRST_MESSAGE);
            controller.modifyModel(firstMessage);

            int num = controller.getNumOfActionTaken();
            controller.setNumOfActionTaken(num + 1);

            //TODO gestione array
            controller.flushMessages();

            if(controller.getNumOfActionTaken() == controller.MAX_NUM_OF_ACTION) {
                controller.setState(new NotYourTurnState(controller, gameHandler));
                gameHandler.nextTurn();
            }
            else {
                controller.setState(new EmptyControllerState(controller, gameHandler));
            }
        }
        else{
            //print the next request
            arg.getAuthorView().printFromController(controller.getCopyMessageListExpected().get(index).getString());
        }
        */
    }



}
