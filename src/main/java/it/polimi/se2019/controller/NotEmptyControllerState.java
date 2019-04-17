package it.polimi.se2019.controller;

import it.polimi.se2019.view.ViewControllerMess.*;

import java.util.ArrayList;

public class NotEmptyControllerState implements StateController {

    private Controller controller;
    private final int FIRST_MESSAGE = 0;

    NotEmptyControllerState(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ActionMessage arg) {
        startingHandler(arg);
        //TODO
        startingHandler(arg);

    }

    @Override
    public void handle(CardSpawnChooseMessage arg) {
        startingHandler(arg);
        //TODO
        startingHandler(arg);

    }

    @Override
    public void handle(CellMessage arg) {
        startingHandler(arg);
        //TODO
        endingHandler();

    }

    @Override
    public void handle(FireModeMessage arg) {
        startingHandler(arg);
        controller.sendTargetsToView(arg);
        startingHandler(arg);

    }

    @Override
    public void handle(NewtonMessage arg) {
        startingHandler(arg);
        controller.sendTargetsToView(arg);
        startingHandler(arg);

    }

    @Override
    public void handle(NopeMessage arg) {
        //TODO è diverso rispetto agli altri!

    }

    @Override
    public void handle(PlayerViewMessage arg) {
        startingHandler(arg);
        //TODO
        startingHandler(arg);

    }

    @Override
    public void handle(ReloadMessage arg) {
        startingHandler(arg);
        //TODO
        startingHandler(arg);

    }

    @Override
    public void handle(TagbackGranateMessage arg) {
        startingHandler(arg);
        controller.sendTargetsToView(arg);
        startingHandler(arg);

    }

    @Override
    public void handle(TargetingScopeMessage arg) {
        startingHandler(arg);
        //TODO
        startingHandler(arg);

    }

    @Override
    public void handle(TargetMessage arg) {
        startingHandler(arg);
        //TODO
        startingHandler(arg);

    }

    @Override
    public void handle(TeleporterMessage arg) {
        startingHandler(arg);
        //TODO
        startingHandler(arg);

    }

    @Override
    public void handle(Object arg) {
        //TODO scrivi eccezione
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
        int expectedID = controller.getMessageListExpected().get(index).getMessage().getMessageID();
        if(arg.getMessageID() == expectedID) {
            ArrayList<ViewControllerMessage> messageListReceived = controller.getMessageListReceived();
            messageListReceived.add(arg);
            controller.setMessageListReceived(messageListReceived);
            controller.setIndexExpected(index + 1);
            return true;
        }
        else {
            String response = controller.getMessageListExpected().get(index).getString();
            arg.getAuthorView().printFromController(response);
            return false;
        }
    }

    /**
     * it handles the end of this entire State. If the sequence of message is ended, it sends them to the model and it changes the State of the controller
     */
    private void endingHandler() {
        if(controller.getMessageListExpected().size() == controller.getIndexExpected()) {
            //Model is modified
            ViewControllerMessage firstMessage = controller.getMessageListReceived().get(FIRST_MESSAGE);
            controller.modifyModel(firstMessage);

            int num = controller.getNumOfActionTaken();
            controller.setNumOfActionTaken(num + 1);

            //TODO gestione array
            controller.flushMessages();

            if(controller.getNumOfActionTaken() == controller.MAX_NUM_OF_ACTION) {
                controller.setState(new NotYourTurnState(controller));
                controller.getGameHandler().nextTurn();
            }
            else {
                controller.setState(new EmptyControllerState(controller));
            }
        }
    }
}
