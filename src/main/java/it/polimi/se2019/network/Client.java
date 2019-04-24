package it.polimi.se2019.network;

public interface Client {

    /**
     * Send user's message to the Server
     * @param message message to send
     */
    void send(String message);
}
