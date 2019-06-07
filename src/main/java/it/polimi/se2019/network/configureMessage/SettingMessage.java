package it.polimi.se2019.network.configureMessage;

import it.polimi.se2019.network.Server;
import it.polimi.se2019.network.WaitingRoom;

public class SettingMessage implements HandlerServerMessage, HandlerConfigMessage {
    private int map;
    private int skulls;
    private boolean suddendDeacth;

    public SettingMessage(int map, int skulls, boolean suddendDeacth) {
        this.map = map;
        this.skulls = skulls;
        this.suddendDeacth = suddendDeacth;
    }

    public int getMap() {
        return map;
    }

    public int getSkulls() {
        return skulls;
    }

    public boolean isSuddendDeacth() {
        return suddendDeacth;
    }

    @Override
    public void handleMessage(WaitingRoom w, Server sender) {
        w.handleSettingMessage(map, skulls, suddendDeacth, sender)   ;
    }

    @Override
    public void handleMessage(SwitchServerMessage s) {
        s.forwardConfigMessage(this);
    }
}
