package it.polimi.se2019.network;

import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

import java.util.Observer;

public interface Client extends Observer {

    /**
     * Connect client to server
     */
    void connect();
}
