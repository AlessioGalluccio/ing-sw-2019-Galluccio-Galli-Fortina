package it.polimi.se2019.controller;

import it.polimi.se2019.view.ViewControllerMess.*;

public class NotYourTurnState implements StateController {

    private Controller controller;
    private final String NOT_YOUR_TURN_RESPONSE = "Please, wait your turn";

    NotYourTurnState(Controller controller) {
        this.controller = controller;
        controller.setNumOfActionTaken(0);
    }

    @Override
    public void handle(ActionMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);
    }

    @Override
    public void handle(CardSpawnChooseMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);
    }

    @Override
    public void handle(CellMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(FireModeMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(NewtonMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(NopeMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(PlayerViewMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(ReloadMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(TagbackGranateMessage arg) {
        //TODO implementami! Sono diverso!

    }

    @Override
    public void handle(TargetingScopeMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(TargetMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);

    }

    @Override
    public void handle(TeleporterMessage arg) {
        arg.getAuthorView().printFromController(NOT_YOUR_TURN_RESPONSE);
    }

    @Override
    public void handle(Object arg) {
        //TODO scrivi eccezione
    }

}
