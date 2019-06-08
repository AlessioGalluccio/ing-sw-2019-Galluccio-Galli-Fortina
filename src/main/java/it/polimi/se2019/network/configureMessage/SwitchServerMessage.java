package it.polimi.se2019.network.configureMessage;

import it.polimi.se2019.network.configureMessage.HandlerServerMessage;

public interface SwitchServerMessage {

    /**
     * When the server received a message of configuration it will be forward to waiting room
     */
    void forwardConfigMessage(HandlerServerMessage message);

    /**
     * When the server received a message of configuration it will be forward to player view
     */
    void forwardViewMessage(HandlerServerMessage message);
}