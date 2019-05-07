package it.polimi.se2019.controller;

import it.polimi.se2019.view.ViewControllerMess.*;

public interface StateController {


    public void handle(ActionMessage arg);

    public void handle(CardSpawnChooseMessage arg);

    public void handle(CellMessage arg);

    public void handle(FireModeMessage arg);

    public void handle(LoginMessage arg);

    public void handle(NewtonMessage arg);

    public void handle(NopeMessage arg);

    public void handle(PlayerMessage arg);

    public void handle(ReloadMessage arg);

    public void handle(TagbackGranateMessage arg);

    public void handle(TargetingScopeMessage arg);

    public void handle(TeleporterMessage arg);

    /**
     * default method. Should not be used
     * @param arg
     */
    public void handle(Object arg);

}
