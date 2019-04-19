package it.polimi.se2019.model.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Mark {
    private static final int MAX_MARK = 3;
    private ArrayList<Integer> markDone = new ArrayList<>();
    private ArrayList<Integer> markReceived = new ArrayList<>();

    //Default constructor

    /**
     *
     * @return list of marks done to other players
     */
    public List<Player> getMarkDone() {
        return duplicateList(markDone);
    }

    /**
     *
     * @return list of marks received by other players
     */
    public List<Player> getMarkReceived() {
        return duplicateList(markReceived);
    }

    /**
     * HELPER METHOD
     * @param listToCopy List to copy
     * @return duplicate list of listToCopy
     */
    private List<Player> duplicateList (List<Integer> listToCopy) {
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(Card.class, new JsonAdapter<Card>())
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>())
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .registerTypeAdapter(PowerupCard.class, new JsonAdapter<PowerupCard>())
                .registerTypeAdapter(Target.class, new JsonAdapter<Target>())
                .registerTypeAdapter(Cell.class, new JsonAdapter<Cell>());

        Gson gson = g.create();

        Type TYPE = new TypeToken<ArrayList<Player>>() {}.getType();

        List<Player> listToReturn = new ArrayList<>();
        for(Integer i : listToCopy) {
            listToReturn.add(GameHandler.getPlayerByID(i));
        }

        return gson.fromJson(gson.toJson(listToReturn, TYPE), TYPE);
    }

    /**
     *
     * @param enemyToMark opponent player you want to mark
     */
    protected void addMarkDoneTo(Player enemyToMark) throws TooManyException {
        if(Collections.frequency(markDone, enemyToMark.getID()) == MAX_MARK) throw new TooManyException("You have already marked three times " + enemyToMark.getNickname());
        else markDone.add(enemyToMark.getID());
    }

    protected void addMarkReceivedBy(Player enemy) {
        markReceived.add(enemy.getID());
    }

    /**
     *
     * @param enemy opponent player whose mark you want to remove from yourself
     */
    protected void removeMarkReceivedBy(Player enemy) {
        Integer enemyID = enemy.getID();
        while(markReceived.remove(enemyID));
    }

    protected void removeMarkDoneTo(Player enemyMarked) {
        Integer enemyID = enemyMarked.getID();
        while(markDone.remove(enemyID));
    }



}
