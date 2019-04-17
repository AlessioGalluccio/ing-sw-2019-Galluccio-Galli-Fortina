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

    public Deck(Stack<T> unusedCard) {
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
    public ArrayList<T> getInUseCard() {
        Gson gson = createGson();
        Type TYPE = getType(true);

        return gson.fromJson(gson.toJson(inUseCard, TYPE), TYPE);
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
     * HELPER METHOD
     * @param ArrayListORStack true if you want an ArrayList, false for a Stack
     * @return Type parametrized of the deck (WeaponCard, PowerupCard, AmmoConvertibleCard)
     */
    protected abstract Type getType(boolean ArrayListORStack);

    /**
     * HELPER METHOD
     * Create a serializer and deserializer for the deck
     * @return a Gson objcet for a deep copy of deck
     */
    private Gson createGson() {
        GsonBuilder g = new GsonBuilder()
                .registerTypeAdapter(Card.class, new JsonAdapter<Card>())
                .registerTypeAdapter(AmmoConvertibleCard.class, new JsonAdapter<AmmoConvertibleCard>())
                .registerTypeAdapter(PowerupCard.class, new JsonAdapter<PowerupCard>())
                .registerTypeAdapter(WeaponCard.class, new JsonAdapter<WeaponCard>())
                .registerTypeAdapter(FireMode.class, new JsonAdapter<FireMode>())
                .registerTypeAdapter(Target.class, new JsonAdapter<Target>());

        return g.create();
    }

    /**
     * Shuffle the discarded cards and refill the deck
     */
    public void mix(){
        Collections.shuffle(usedCard);
        unusedCard.addAll(usedCard);
        usedCard.clear();
    }

    /**
     * Pick the first card of the deck
     * @return The first elements of the deck
     */
    public T pick(){
        T picked = unusedCard.pop();
        inUseCard.add(picked);
        return picked;
    }

    /**
     * Move the card in the discarted pile
     * DON'T USE WITH THOSE CARDS WHICH DOSE NOT HAVE A DISCARTED PILE
     * @param card Card to discard
     */
    public void discard(T card) {
        inUseCard.remove(card);
        usedCard.add(card);
    }
}
