package it.polimi.se2019.model.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;

import java.util.List;
import java.util.ArrayList;

public class CellAmmo extends Cell {
    private AmmoCard ammo;
    private transient AmmoDeck deck;

    public CellAmmo(Border north, Border east, Border south, Border west, int x, int y, AmmoDeck deck) {
        super(north, east, south, west, x, y);
        this.deck = deck;
    }


    @Override
    public Card grabCard(int cardID) throws NotCardException {
        if(ammo == null) throw new NotCardException("Card already taken");
        Card cardToReturn = ammo;
        ammo = null;
        return cardToReturn;
    }

    @Override
    public List<Integer> getCardID() {
        ArrayList<Integer> cardId = new ArrayList<>();
        cardId.add(0);
        return cardId;
    }

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    @Override
    protected void reloadCard() {
        if(ammo==null) ammo = deck.pick();
    }

    public Cell clone() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AmmoCard.class, new JsonAdapter<AmmoCard>())
                .registerTypeAdapter(Border.class, new JsonAdapter<Border>())
                .registerTypeAdapter(Cell.class, new JsonAdapter<Cell>())
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        return gson.fromJson(gson.toJson(this), CellAmmo.class);
    }
}
