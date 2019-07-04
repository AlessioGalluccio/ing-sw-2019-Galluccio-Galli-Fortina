package it.polimi.se2019.network.messages;

import it.polimi.se2019.network.Server;
import it.polimi.se2019.network.WaitingRoom;

import java.io.Serializable;

public class SettingMessage implements HandlerServerMessage, HandlerConfigMessage, Serializable {
    private static final long serialVersionUID = -2954599084019987269L;
    private int map;
    private int skulls;
    private boolean suddendDeath;

    public SettingMessage(int map, int skulls, boolean suddendDeath) {
        this.map = map;
        this.skulls = skulls;
        this.suddendDeath = suddendDeath;
    }

    public int getMap() {
        return map;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of WaitingRoom in order to handle the setting chosen correctly.
     * @param w The waiting room who has to handle this message
     * @param sender The server who send this message
     */
    @Override
    public void handleMessage(WaitingRoom w, Server sender) {
        w.handleSettingMessage(map, skulls, suddendDeath)   ;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the object which receives this message in order to forward him correctly.
     * @param s The object who has to switch this message
     */
    @Override
    public void handleMessage(SwitchServerMessage s) {
        s.forwardConfigMessage(this);
    }
}
