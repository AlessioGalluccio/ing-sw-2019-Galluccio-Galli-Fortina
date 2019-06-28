package it.polimi.se2019.model.deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2019.model.JsonAdapter;

import java.lang.reflect.Type;
import java.util.*;

public abstract class Deck<T> {

    private Stack<T> unusedCard;
    private ArrayList<T> inUseCard;
    private Stack<T> usedCard;

    Deck(Stack<T> unusedCard) {
        this.unusedCard = unusedCard;
        this.inUseCard = new ArrayList<>();
        this.usedCard = new Stack();

        for(int i=0; i<unusedCard.size(); i++) {
            Card c = (Card) unusedCard.get(i);
            c.setDeck(this);
        }
    }

    /**
     *
     * @return Deep copy of unsedCard stack
     */
    public Stack<T> getUnusedCard() {
        Gson gson = createGson();
        Type TYPE = getType(false);

        return gson.fromJson(gson.toJson(unusedCard, TYPE), TYPE);
    }

    /**
     *
     * @return Deep copy of inUsedCard stack
     */
    List<T> getInUseCard() {
        Gson gson = createGson();
        Type TYPE = getType(true);

        return gson.fromJson(gson.toJson(inUseCard, TYPE), TYPE);
    }

    /**
     * Return the number of cards in the deck
     * @return number of cards in the deck
     */
    int sizeDeck() {
        return unusedCard.size() +
                usedCard.size() +
                inUseCard.size();
    }

    /**
     * Return number of cards not already picked
     * @return number of cards not already picked
     */
    public int sizeUnususedCard() {
        return unusedCard.size();
    }

    /**
     *
     * @return Deep copy of usedCard
     */
    public Stack<T> getUsedCard() {
        Gson gson = createGson();
        Type TYPE = getType(false);

        return gson.fromJson(gson.toJson(usedCard, TYPE), TYPE);
    }

    /**
     * Used by Gson to serialize the deck.
     * Return a Type object according to the param: ArrayList or Stack
     * @param arrayListORStack True if you want an ArrayList, false for a Stack
     * @return A Type object of the specify class, parametrized of the deck (WeaponCard, PowerupCard, AmmoCard)
     */
    protected abstract Type getType(boolean arrayListORStack);

    /**
     * Create a serializer and deserializer for the deck
     * @return a Gson object for a deep copy of deck
     */
    private Gson createGson() {
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(Card.class, new JsonAdapter<Card>())
                .registerTypeAdapter(AmmoCard.class, new JsonAdapter<AmmoCard>())
                .registerTypeAdapter(PowerupCard.class, new JsonAdapter<PowerupCard>())
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>())
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .registerTypeAdapter(Target.class, new JsonAdapter<Target>());

        return g.create();
    }

    /**
     * Shuffle the discarded cards and refill the deck
     */
    private void mix(){
        Collections.shuffle(usedCard);
        unusedCard.addAll(usedCard);
        usedCard.clear();
    }

    /**
     * Pick the first card of the deck
     * If the card picked is the last one, mix the deck
     * @return The first elements of the deck
     */
    public T pick(){
        T picked = unusedCard.pop();
        inUseCard.add(picked);
        if(unusedCard.isEmpty()) mix();
        return picked;
    }

    /**
     * Move the card in the discarded pile
     * DON'T USE WITH THOSE CARDS WHICH DOSE NOT HAVE A DISCARDED PILE
     * @param card Card to discard
     */
    protected void discard(T card) {
        inUseCard.remove(card);
        usedCard.add(card);
    }

    /**
     * Return the card corresponding to the id
     * @param id Card's id
     * @return The card with that id, null if there's not
     */
    public T getCardById(int id) {
        for(int i=0; i<inUseCard.size(); i++) {
            Card c = (Card) inUseCard.get(i);
            if(c.getID() == id) return inUseCard.get(i);
        }
        for(int i=0; i<unusedCard.size(); i++) {
            Card c = (Card) unusedCard.get(i);
            if(c.getID() == id) return unusedCard.get(i);
        }
        for(int i=0; i<usedCard.size(); i++) {
            Card c = (Card) usedCard.get(i);
            if(c.getID() == id) return usedCard.get(i);
        }
        return null;
    }
}
