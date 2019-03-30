package it.polimi.se2019.model.player;

import java.util.ArrayList;

public class Mark {
    private ArrayList<Player> markDone;
    private ArrayList<Player> markReceived;

    public Mark() {

    }

    /**
     *
     * @return list of marks done to other players
     */
    public ArrayList<Player> getMarkDone() {

    }

    /**
     *
     * @return list of marks recieved by other players
     */
    public ArrayList<Player> getMarkReceived() {
        return markReceived;
    }

    /**
     *
     * @param target opponent player you want to mark
     */
    public void setMark(Player target) {

    }

    /**
     *
     * @param target opponent player whose mark you want to remove from yourself
     */
    public void removeMark(Player target) {

    }



}
