package it.polimi.se2019.network.messages;

import it.polimi.se2019.network.Server;
import it.polimi.se2019.network.WaitingRoom;

public interface HandlerConfigMessage {

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of WaitingRoom in order to handle him correctly.
     * @param w The waiting room who has to handle this message
     * @param sender The server which  send this message
     */
    void handleMessage(WaitingRoom w, Server sender);
}
