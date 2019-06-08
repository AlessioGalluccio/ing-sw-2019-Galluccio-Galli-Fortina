package it.polimi.se2019.network.configureMessage;

import it.polimi.se2019.network.Server;
import it.polimi.se2019.network.WaitingRoom;

import java.io.Serializable;

public class SettingMessage implements HandlerServerMessage, HandlerConfigMessage, Serializable {
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

    public int getSkulls() {
        return skulls;
    }

    public boolean isSuddendDeacth() {
        return suddendDeath;
    }

    @Override
    public void handleMessage(WaitingRoom w, Server sender) {
        w.handleSettingMessage(map, skulls, suddendDeath, sender)   ;
    }

    @Override
    public void handleMessage(SwitchServerMessage s) {
        s.forwardConfigMessage(this);
    }
}
