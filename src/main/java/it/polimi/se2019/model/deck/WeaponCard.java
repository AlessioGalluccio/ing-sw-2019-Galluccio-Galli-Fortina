package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;

import java.util.ArrayList;

public abstract class WeaponCard implements Card {

    private ColorRYB ammoGranted;
    private ArrayList<ColorRYB> ammoNotGranted;
    private String name;
    private String description;
    private ColorRYB color;
    private boolean reload;

    public ArrayList<ColorRYB> getBuyCost(){

        return null; //TODO implementare
    }

    public ArrayList<Color> getReloadCost() {

        return null; //TODO implementare
    }

    public String getName(){
        return name;
    }

    public boolean isReloaded(){

        return reload; //TODO implementare
    }

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {

    }

    @Override
    public void discard() {

    }

    @Override
    public void useCard() {

    }
}
