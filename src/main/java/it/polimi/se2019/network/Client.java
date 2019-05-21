package it.polimi.se2019.network;

import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

public interface Client {

    /**
     * Send user's message to the Server
     * @param message message to send
     */
    void send(ViewControllerMessage message);

    /**
     * Connect client to server
     */
    void connect();
}
