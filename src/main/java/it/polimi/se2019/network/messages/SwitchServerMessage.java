package it.polimi.se2019.network.messages;

import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;

/**
 * @author Galli
 */
public interface SwitchServerMessage {

    /**
     * When the server received a message of configuration it will be forward to waiting room
     * @param message the message to forward
     */
    void forwardConfigMessage(HandlerServerMessage message);

    /**
     * When the server received a message of configuration it will be forward to player view
     * @param message the message to forward
     */
    void forwardViewMessage(ViewControllerMessage message);
}
