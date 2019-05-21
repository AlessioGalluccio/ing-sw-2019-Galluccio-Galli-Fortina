package it.polimi.se2019.model.map;

import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2019.cloneable.SkinnyObjectExclusionStrategy;
import it.polimi.se2019.model.JsonAdapter;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.TooManyException;

public class CellSpawn extends Cell {
    private final int MAX_WEAPONCARD = 3;
    private WeaponCard[] weapon = new WeaponCard[MAX_WEAPONCARD];
    private ColorRYB color;
    private transient WeaponDeck deck;

    protected CellSpawn(Border north, Border east, Border south, Border west, int x, int y, WeaponDeck deck) {
        super(north, east, south, west, x, y);
        this.deck = deck;
    }

    /**
     *
     * @return list of WeaponCard that can be pick here
     */
    public List<WeaponCard> getWeapon() {

        return null; //TODO implementare
    }

    public ColorRYB getColor() {
        return color;
    }

    @Override
    public Card grabCard(int cardID) throws NotCardException {
        WeaponCard cardToReturn = null;
        for(int i=0; i<MAX_WEAPONCARD; i++) {
            if(weapon[i]!=null && weapon[i].getID() == cardID) {
                cardToReturn = weapon[i];
                weapon[i] = null;
            }
        }
        if(cardToReturn!=null) return cardToReturn;
        throw new NotCardException("This card is not here! (Already taken or doesn't exist)");
    }

    @Override
    public List<Integer> getCardID() {
        ArrayList <Integer> cardId = new ArrayList<>();
        for(WeaponCard card : weapon) {
            if(card != null) cardId.add(card.getID());
        }
        return cardId;
    }

    /**
     * If the card on the cell was picked, put a new card on the cell
     */
    @Override
    protected void reloadCard(){
        for(int i=0; i<MAX_WEAPONCARD; i++) {
            if(weapon[i]==null && deck.sizeUnususedCard()!=0) weapon[i]=deck.pick();
        }
    }

    /**
     * If a player has to discard a Weapon card, this method replace a empty card space with the discarded card
     * @param card card discarded
     * @throws TooManyException if there are already 3 cards on this cell
     */
    //In order to use this method you have to cast Cell in SpawnCell!!
    public void replaceCard(WeaponCard card) throws TooManyException {
        for(int i=0; i<MAX_WEAPONCARD; i++) {
            if(weapon[i]==null) {
                weapon[i] = card;
                return;
            }
        }
        throw new TooManyException("In this cell there are already 3 cards");
    }

    @Override
    protected void setRoom(Room room) {
        super.setRoom(room);
        String colorRoom = getRoom().getColor().toUpperCase();
        switch (colorRoom) {
            case "BLUE":
                color = ColorRYB.BLUE; break;
            case "YELLOW":
                color = ColorRYB.YELLOW; break;
            case "RED":
                color = ColorRYB.RED; break;
            default:
                throw new IllegalArgumentException("Color room is not one of ColorRYB");
        }
    }

    @Override
    public Cell clone() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>())
                .registerTypeAdapter(Border.class, new JsonAdapter<Border>())
                .registerTypeAdapter(Cell.class, new JsonAdapter<Cell>())
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .setExclusionStrategies(new SkinnyObjectExclusionStrategy())
                .create();

        return gson.fromJson(gson.toJson(this), CellSpawn.class);
    }

}
