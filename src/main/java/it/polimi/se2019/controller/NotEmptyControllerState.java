package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;

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
    public void handle(ActionMessage arg) {
        if(startingHandler(arg)){
            //TODO
            endingHandler(arg);
        }

    }

    @Override
    public void handle(CardSpawnChooseMessage arg) {
        if(startingHandler(arg)){
            //TODO
            endingHandler(arg);
        }

    }

    @Override
    public void handle(CellMessage arg) {
        if(startingHandler(arg)){
            //TODO
            endingHandler(arg);
        }
    }

    @Override
    public void handle(FireModeMessage arg) {
        Player player = gameHandler.getPlayerByID(arg.getAuthorID());
        FireMode fireMode = getFireModeByID(arg.getFiremodeID());
        AmmoBag cost = AmmoBag.createAmmoFromList(fireMode.getCost());

        if(!player.canPayAmmo(cost)){
            arg.getAuthorView().printFromController("Not enough ammo for this firemode");
            return;
        }
        else{
            controller.sendTargetsToView(arg);
            endingHandler(arg);
        }
    }

    @Override
    public void handle(NewtonMessage arg) {
        if(startingHandler(arg)){
            controller.sendTargetsToView(arg);
            endingHandler(arg);
        }
    }

    @Override
    public void handle(NopeMessage arg) {
        int index = controller.getIndexExpected();
        if(controller.getCopyMessageListExpected().get(index).isOptional()){
            endingHandler(arg);
        }
        else{
            String response = controller.getCopyMessageListExpected().get(index).getString();
            arg.getAuthorView().printFromController(response);
        }
    }

    @Override
    public void handle(PlayerViewMessage arg) {
        if(startingHandler(arg)){
            //TODO
            endingHandler(arg);
        }

    }

    @Override
    public void handle(ReloadMessage arg) {
        if(startingHandler(arg)){
            //TODO
            endingHandler(arg);
        }
    }

    @Override
    public void handle(TagbackGranateMessage arg) {
        if(startingHandler(arg)){
            controller.sendTargetsToView(arg);
            endingHandler(arg);
        }

    }

    @Override
    public void handle(TargetingScopeMessage arg) {
        if(startingHandler(arg)){
            //TODO
            endingHandler(arg);
        }

    }

    @Override
    public void handle(TargetMessage arg) {
        if(startingHandler(arg)){
            //TODO
            endingHandler(arg);
        }

    }

    @Override
    public void handle(TeleporterMessage arg) {
        if(startingHandler(arg)){
            //TODO
            endingHandler(arg);
        }

    }

    /**
     * it should be not used
     * @param arg
     */
    @Override
    public void handle(Object arg) {
        throw new IllegalArgumentException();
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
    }
}
