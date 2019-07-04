package it.polimi.se2019.model.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * @author Galli
 */
public class Mark implements Serializable {
    private static final int MAX_MARK = 3;
    private static final long serialVersionUID = 7677576860792993751L;
    private ArrayList<Player> markDone = new ArrayList<>();
    private ArrayList<Player> markReceived = new ArrayList<>();

    //Default constructor

    /**
     *
     * @return list of marks done to other players
     */
    public List<Player> getMarkDone() {
        return new ArrayList<>(markDone);
    }

    /**
     * Return the number of mark done to a specific enemy
     * @param target The enemy
     * @return The number of mark done to param target
     */
    public int getNumMarkDoneTo(Player target){
        int numMarks = 0;
        for(Player temp : markDone){
            if(temp.getID() == target.getID()){
                numMarks++;
            }
        }
        return numMarks;
    }

    /**
     *
     * @return list of marks received by other players
     */
    public List<Player> getMarkReceived() {
        return new ArrayList<>(markReceived);
    }

    /**
     * Keep trace of the mark to an enemy
     * This method add a mark done to an enemy
     * @param enemyToMark opponent player you want to mark
     * @throws TooManyException If try to add more than three marks on the same enemy
     */
    void addMarkDoneTo(Player enemyToMark) throws TooManyException {
        if(Collections.frequency(markDone, enemyToMark) == MAX_MARK) throw new TooManyException("You have already marked three times " + enemyToMark.getNickname());
        else markDone.add(enemyToMark);
    }

    /**
     * Keep trace of the mark received by an enemy
     * This method add a mark received by an enemy
     * @param enemy The enemy who marked you
     */
    void addMarkReceivedBy(Player enemy) {
        markReceived.add(enemy);
    }

    /**
     * Keep trace of the mark received by an enemy
     * This method remove all the marks received by an enemy
     * @param enemy opponent player whose mark you want to remove from yourself
     */
    void removeMarkReceivedBy(Player enemy) {
        while(markReceived.remove(enemy));
    }

    /**
     * Keep trace of the mark to an enemy
     * This method remove all the marks done to an enemy
     * @param enemyMarked the enemy you had marked and want to un-mark
     */
    void removeMarkDoneTo(Player enemyMarked) {
        while(markDone.remove(enemyMarked));
    }

    /**
     * Deep copy of mark
     * Not all the attributes are copied, only the NOT transient!
     * The player who make damage are not all copy, only not transient attributes and not SkinnyObject
     * @return deep copy of mark
     */
    protected Mark clone() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        return gson.fromJson(gson.toJson(this), Mark.class);
    }


}
