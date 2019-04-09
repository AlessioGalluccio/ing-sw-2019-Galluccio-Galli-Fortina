package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.player.Player;

public class Death {

    private final Player whoKilled;
    private final Player whoDied;
    private int points;

    //constants
    private static final int POINTS_OVERKILLED = 2;
    private static final  int POINTS_NOT_OVERKILLED = 1;

    public Death(Player whoKilled, Player whoDied) {
        //TODO exception if Player whoDied is not dead

        this.whoKilled = whoKilled;
        this.whoDied = whoDied;

        if(whoDied.isOverKilled()) {
            points = POINTS_OVERKILLED;
        }
        else {
            points = POINTS_NOT_OVERKILLED;
        }
    }

    /**
     *
     * @return Player who killed in this kill
     */
    public Player getWhoKilled(){
        return whoKilled;
    }

    /**
     *
     * @return Player who died in this kill
     */
    public Player getWhoDied(){
        return whoDied;
    }

    /**
     *
     * @return points gained by the Player who killed
     */
    public int getPoints(){
        return points;
    }
}
