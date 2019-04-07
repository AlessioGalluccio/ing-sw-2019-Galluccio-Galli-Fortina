package it.polimi.se2019.model.deck;

import java.util.ArrayList;

public abstract class Deck {

    private ArrayList<Card> unusedCard;
    private ArrayList<Card> inUseCard;
    private ArrayList<Card> usedCard;


    public Deck() {
    }

    public void mix(){

    }

    public Card pick(){

        return null; //TODO implementare
    }

    public void discard( Card A ){

    }
}
