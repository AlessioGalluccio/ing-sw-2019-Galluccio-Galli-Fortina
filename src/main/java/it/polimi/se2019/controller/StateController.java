package it.polimi.se2019.controller;

import it.polimi.se2019.view.ViewControllerMess.*;

public interface StateController {


    public void handle(ActionMessage arg);

    public void handle(CardSpawnChooseMessage arg);

    public void handle(CellMessage arg);

    public void handle(FireModeMessage arg);

    public void handle(NewtonMessage arg);

    public void handle(NopeMessage arg);

    public void handle(PlayerViewMessage arg);

    public void handle(ReloadMessage arg);

    public void handle(TagbackGranateMessage arg);

    public void handle(TargetingScopeMessage arg);

    public void handle(TargetMessage arg);

    public void handle(TeleporterMessage arg);

    /**
     * default method. SHould not be used
     * @param arg
     */
    public void handle(ViewControllerMessage arg);

}
