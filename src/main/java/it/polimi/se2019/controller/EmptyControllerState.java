package it.polimi.se2019.controller;

import it.polimi.se2019.view.ViewControllerMess.*;

public class EmptyControllerState implements  StateController {

    private Controller controller;

    EmptyControllerState(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ActionMessage arg) {

    }

    @Override
    public void handle(CardSpawnChooseMessage arg) {

    }

    @Override
    public void handle(CellMessage arg) {

    }

    @Override
    public void handle(FireModeMessage arg) {

    }

    @Override
    public void handle(NewtonMessage arg) {

    }

    @Override
    public void handle(NopeMessage arg) {

    }

    @Override
    public void handle(PlayerViewMessage arg) {

    }

    @Override
    public void handle(ReloadMessage arg) {

    }

    @Override
    public void handle(TagbackGranateMessage arg) {

    }

    @Override
    public void handle(TargetingScopeMessage arg) {

    }

    @Override
    public void handle(TargetMessage arg) {

    }

    @Override
    public void handle(TeleporterMessage arg) {

    }

    @Override
    public void handle(ViewControllerMessage arg) {

    }
}
