package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.List;

import static it.polimi.se2019.model.handler.GameHandler.getFireModeByID;

public class NotEmptyControllerState implements StateController {

    private Controller controller;
    private GameHandler gameHandler;
    private static final int FIRST_MESSAGE = 0;

    NotEmptyControllerState(Controller controller, GameHandler gameHandler) {
        this.controller = controller;
        this.gameHandler = gameHandler;
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
        //TODO
    }

    @Override
    public void handleFiremode(int firemodeID) {
        Player player = gameHandler.getPlayerByID(controller.getLastReceivedMessage().getAuthorID());
        FireMode fireMode = getFireModeByID(firemodeID);
        AmmoBag cost = AmmoBag.createAmmoFromList(fireMode.getCost());

        if(!player.canPayAmmo(cost)){
            controller.getLastReceivedMessage().getAuthorView().printFromController("Not enough ammo for this firemode");
            controller.removeLastReceivedMessage();
        }
        else{
            //TODO sistema
            //controller.sendTargetsToView(controller.getLastReceivedMessage());
        }
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
        int index = controller.getIndexExpected();
        if(!controller.getCopyMessageListExpected().get(index).isOptional()){
            String response = controller.getCopyMessageListExpected().get(index).getString();
            controller.getLastReceivedMessage().getAuthorView().printFromController(response);
            controller.removeLastReceivedMessage();
        }
        else{
            //do nothing
        }

    }

    @Override
    public void handlePlayer(int playerID) {
        //TODO

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
    public void handleTargeting(TargetingScopeCard usedCard, ColorRYB colorAmmo) {
        AmmoBag cost = TargetingScopeMessage.getCost(colorAmmo);
        Player player = gameHandler.getPlayerByID(controller.getLastReceivedMessage().getAuthorID());
        int indexOfPrevious = (controller.getIndexExpected() - 1);
        List<ViewControllerMessage> stack = controller.getCopyMessageListReceived();
        //TODO
        /*
        if(isPlayerTargetMessage(stack.get(indexOfPrevious))){
            for(int i = 0; i < indexOfPrevious; i++){
                cost = addCost(stack.get(i), cost);
                //cost is controlled (other targeting scopes and the last firemode)
                if(!player.canPayAmmo(cost)){
                    controller.getLastReceivedMessage().getAuthorView().printFromController("You don't have enough Ammo for this Targeting Scope");
                    return;
                }
                try{
                    if(isFiremode(stack.get(i))){
                        //you can add targeting massage
                        if(isThisTargetingAlreadyPresent(stack, arg.getUsedCard())){
                            controller.addMessageListReceived(arg);
                            return;
                        }
                    }
                }catch(FiremodeOfOnlyMarksException e){
                    //this firemode adds only marks, you can't use this card
                    controller.getLastReceivedMessage().getAuthorView().printFromController("You can't use targeting scope with this firemode");
                    return;
                }

            }
        }
        else{
            return;
        }

        */

    }

    @Override
    public void handleTeleporter(TeleporterCard usedCard) {
        //TODO

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
        if(arg.getMessageID() == expectedID) {
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
        int index = controller.getIndexExpected();
        controller.addMessageListReceived(arg);
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
    }

    private boolean isPlayerTargetMessage(ViewControllerMessage arg){
        return false;
    }

    private boolean isPlayerTargetMessage(PlayerMessage arg){
        return true;
    }

    private boolean isFiremode(ViewControllerMessage arg) throws FiremodeOfOnlyMarksException {
        return false;
    }

    private boolean isFiremode(FireModeMessage arg) throws FiremodeOfOnlyMarksException{
        //TODO sistema controllo per il lancio dell'eccezione
        if(getFireModeByID(arg.getFiremodeID()).giveOnlyMarks()){
            throw new FiremodeOfOnlyMarksException();
        }
        return true;
    }

    private boolean isTargetingScopeUsed(ViewControllerMessage arg, TargetingScopeCard targetingScopeCard){
        return false;
    }

    private boolean isTargetingScopeUsed(TargetingScopeMessage arg, TargetingScopeCard targetingScopeCard){
        //TODO sistema
        return arg.getUsedCard().equals(targetingScopeCard);

    }


/*
    private boolean isThisTargetingAlreadyPresent(List<ViewControllerMessage> stack, TargetingScopeCard targetingScopeCard){
        for(ViewControllerMessage msg : stack){
            if(isTargetingScopeUsed(msg, targetingScopeCard)){
                return true;
            }
        }
        return false;
    }
    */

    /**
     * does nothing, because it doesn't have cost like Firemode or TargetingScope
     * @param msg
     * @param cost
     */
    private AmmoBag addCost(ViewControllerMessage msg, AmmoBag cost){
        //it does nothing
        return cost;
    }

    /**
     * add the cost of the card in cost
     * @param msg the message
     * @param cost the AmmoBag that will be updated
     */
    //private AmmoBag addCost(TargetingScopeMessage msg, AmmoBag cost){
        //it returns the sum
        //return AmmoBag.sumAmmoBag(cost, msg.getCost());
     // }


    /**
     * add the cost of the card in cost
     * @param msg the message
     * @param cost the AmmoBag that will be updated
     */
    private void addCost(FireMode msg, AmmoBag cost){
        cost = AmmoBag.sumAmmoBag(AmmoBag.createAmmoFromList(msg.getCost()), cost);
    }
}
