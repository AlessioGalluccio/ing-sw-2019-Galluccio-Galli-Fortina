package it.polimi.se2019.model.deck;

import java.util.*;

public abstract class Deck<T> {

    private Stack<T> unusedCard;
    private ArrayList<T> inUseCard;
    private Stack<T> usedCard;

    public Deck(Stack<T> unusedCard) {
        this.unusedCard = unusedCard;
        this.inUseCard = new ArrayList<>();
        this.usedCard = new Stack();
    }

    public Stack<T> getUnusedCard() {
        return unusedCard;
    }

    public ArrayList<T> getInUseCard() {
        return inUseCard;
    }

    public Stack<T> getUsedCard() {
        return usedCard;
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


    public void discard(T A) {

    }
}
