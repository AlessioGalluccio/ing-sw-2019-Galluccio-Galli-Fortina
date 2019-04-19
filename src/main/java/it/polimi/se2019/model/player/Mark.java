package it.polimi.se2019.model.player;

import java.util.ArrayList;
import java.util.List;

public class Mark {
    private ArrayList<Player> markDone;
    private ArrayList<Player> markReceived;

    Mark() {

    }

    /**
     *
     * @return list of marks done to other players
     */
    public List<Player> getMarkDone() {

        return null; //TODO implementare
    }

    /**
     *
     * @return list of marks recieved by other players
     */
    public List<Player> getMarkReceived() {
        return markReceived;
    }

    /**
     *
     * @param target opponent player you want to mark
     */
    protected void setMark(Player target) {

    }

    /**
     *
     * @param target opponent player whose mark you want to remove from yourself
     */
    protected void removeMark(Player target) {

    }



}
