package it.polimi.se2019.network;

import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.Observer;

public interface Server extends Observer {

    /**
     * et the playerView to the server
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

    void setMatchID(int matchID);
}
