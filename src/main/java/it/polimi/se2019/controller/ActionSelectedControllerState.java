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


public class ActionSelectedControllerState implements StateController {

    private Player player;
    private PlayerView playerView;
    private Controller controller;
    private GameHandler gameHandler;
    private Action action;
    private static final int FIRST_MESSAGE = 0;

    private final String PLAYER_WRONG = "You can't select this target";
    private final String CELL_WRONG = "You can't select this cell";
    private final String OPTIONAL_WRONG = "You can't select this optional effect";
    private final String NOT_ENOUGH = "You don't have enough Ammo for this. Try discard some Powerups";
    private final String ONLY_MARKS = "The firemode selected gives only marks, you can't use targeting";
    private final String CARD_NOT_PRESENT = "The player doesn't have this card";
    private final String ALREADY_SELECTED = "You have already selected this, you can't use again";

    public ActionSelectedControllerState(Controller controller, GameHandler gameHandler, Action action) {
        //TODO aggiungere player e playerView (anche a tutti gli stati!)
        this.controller = controller;
        this.gameHandler = gameHandler;
        this.player = controller.getAuthor();
        this.playerView = controller.getPlayerView();
        this.action = action;
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
            playerView.printFromController(CELL_WRONG);
            controller.removeReceived();
        }
    }

    @Override
    public void handleFiremode(int firemodeID) {
        try{
            action.addFireMode(firemodeID);
        }catch (WrongInputException e){
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
            playerView.printFromController(PLAYER_WRONG);
            controller.removeReceived();
        }

    }

    @Override
    public void handleOptional(int numOptional) {
        try{
            action.addOptional(numOptional);
        }catch (WrongInputException e){
            playerView.printFromController(OPTIONAL_WRONG);
            controller.removeReceived();
        }catch (NotEnoughAmmoException e){
            playerView.printFromController(NOT_ENOUGH);
            controller.removeReceived();
        }
    }

    @Override
    public void handleReload(int weaponID) {
        //TODO
    }

    @Override
    public void handleTagback(TagbackGranedCard usedCard) {
        //TODO

    }

    @Override
    public void handleTargeting(TargetingScopeCard usedCard, AmmoBag cost) {
        try{
            action.addTargetingScope(usedCard.getID(), cost);

        }catch(WrongInputException e){
            playerView.printFromController(ALREADY_SELECTED);
            controller.removeReceived();

        }catch(NotEnoughAmmoException e){
            playerView.printFromController(NOT_ENOUGH);
            controller.removeReceived();

        }catch(NotPresentException e){
            playerView.printFromController(CARD_NOT_PRESENT);
            controller.removeReceived();

        }catch (FiremodeOfOnlyMarksException e){
            playerView.printFromController(ONLY_MARKS);
            controller.removeReceived();
        }

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        //TODO

    }

    @Override
    public void handleWeaponCard(WeaponCard usedCard) {
        try{
            action.addWeapon(usedCard);
        }catch(WrongInputException e){
            //TODO
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
        }catch(WrongInputException e){
            //TODO
        }
    }

    @Override
    public void handleReconnection(boolean isConnected) {
        //TODO controlla da sistemare sicuramente
        if(!isConnected){
            gameHandler.setPlayerConnectionStatus(player, false);
            gameHandler.nextTurn();
            controller.setState(new DisconnectedControllerState(controller, gameHandler));
        }
    }

    @Override
    public void handle(ViewControllerMessage arg) {
        if(startingHandler(arg)){
            arg.handle(this);
            endingHandler(arg);
        }
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
            return true;
        }
        else {
            String response = controller.getCopyMessageListExpected().get(index).getString();
            arg.getAuthorView().printFromController(response);
            return false;
        }
    }

    /**
     * it handles the end of this entire State. If the sequence of message is ended, it sends them to the model and it changes the State of the controller
     */
    private void endingHandler(ViewControllerMessage arg) {
        //print next request
        String response = controller.getCopyMessageListExpected().get(controller.getIndexExpected()).getString();
        playerView.printFromController(response);
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
