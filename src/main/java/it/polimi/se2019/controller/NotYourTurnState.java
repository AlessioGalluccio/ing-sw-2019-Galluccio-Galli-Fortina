package it.polimi.se2019.controller;

import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.view.ViewControllerMess.*;

public class NotYourTurnState implements StateController {

    private Controller controller;
    private GameHandler gameHandler;
    private final String NOT_YOUR_TURN_RESPONSE = "Please, wait your turn";

    NotYourTurnState(Controller controller, GameHandler gameHandler) {
        this.controller = controller;
        this.gameHandler = gameHandler;
        controller.setNumOfActionTaken(0);
    }

    @Override
    public void handle(ActionMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);
    }

    @Override
    public void handle(CardSpawnChooseMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);
    }

    @Override
    public void handle(CellMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(FireModeMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(NewtonMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(NopeMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(PlayerViewMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(ReloadMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(TagbackGranateMessage arg) {
        controllTurn(arg);
        //TODO implementami! Sono diverso!

    }

    @Override
    public void handle(TargetingScopeMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(TargetMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(TeleporterMessage arg) {
        controllTurn(arg);
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);
    }

    @Override
    public void handle(Object arg) {
        //TODO scrivi eccezione
        throw new IllegalArgumentException();
    }

    /**
     * controlls if it's the turn of the player. If it is, it changes the state and it passes the message to the new state
     * @param arg the message arrived
     */
    private void controllTurn(ViewControllerMessage arg) {
        int IDPlayer = arg.getAuthorID();
        if(IDPlayer == gameHandler.getTurnPlayerID()) {
            StateController nextState = new EmptyControllerState(controller, gameHandler);
            nextState.handle(arg);
            controller.setState(nextState);
        }
    }
}
