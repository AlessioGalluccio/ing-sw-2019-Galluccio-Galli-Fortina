package it.polimi.se2019.network.messages;

import it.polimi.se2019.network.Server;
import it.polimi.se2019.network.WaitingRoom;

public interface HandlerConfigMessage {

    void handleMessage(WaitingRoom w, Server sender);
}
