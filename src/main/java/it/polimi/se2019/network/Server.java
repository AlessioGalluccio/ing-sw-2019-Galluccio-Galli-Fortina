package it.polimi.se2019.network;

import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.Observer;

public interface Server extends Observer {

    /**
     * Set the playerView to the server
     * @param pw the player view to set
     */
    void setPlayerView(PlayerView pw);

    /**
     * Send any kind of text to the user
     * @param string text to send
     */
    void send(String string);  // used by controller

    /**
     * Set a timer long as specified in the config file
     */
    void setTimer();

    /**
     * If there's a timer set, it will be deleted
     */
    void cancelTimer();

    /**
     * The the match's ID of this server object
     * @param matchID The id of this match
     */
    void setMatchID(int matchID);

    /**
     * Close all stream open and terminate all the thread in order to disconnect correctly a client
     */
    void closeAll();
}
