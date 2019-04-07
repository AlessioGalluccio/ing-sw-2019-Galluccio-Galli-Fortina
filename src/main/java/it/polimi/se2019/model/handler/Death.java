package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.player.Player;

public class Death {

    private Player whoKilled;
    private Player whoDied;
    private int points;

    public Death() {
    }

    public Player getWhoKilled(){
        return whoKilled;
    }

    public Player getWhoDied(){
        return whoDied;
    }

    public int getPoints(){
        return points;
    }
}
