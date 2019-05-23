package it.polimi.se2019.network;

import it.polimi.se2019.view.ModelViewMess.ModelViewMessage;
import java.util.Observer;

public interface Server extends Observer {

    /**
     * Send any kind of text to the user
     * @param string text to send
     */
    void send(String string);  // used by controller

    /**
     * Forward a player copy from virtual view to the client
     */
    void send(ModelViewMessage message); //used in Update()

    /**
     * Set a timer long as specified in the config file
     */
    void setTimer();

    /**
     * If there's a timer set, it will be deleted
     */
    void cancelTimer();
}
