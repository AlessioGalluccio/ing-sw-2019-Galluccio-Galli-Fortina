package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import java.util.ArrayList;

public abstract class WeaponCard implements Card {

    private Color ammoGranted;
    private ArrayList<Color> ammoNotGranted;
    private String name;
    private String description;
    private Color color;
    private boolean reload;

    public WeaponCard() {

    }

    public ArrayList<Color> getBuyCost(){

        return null; //TODO implementare
    }

    public ArrayList<Color> getReloadCost() {

        return null; //TODO implementare
    }

    public String getName(){

        return null; //TODO implementare
    }

    public boolean isReloaded(){

        return reload; //TODO implementare
    }

    @Override
    public void useCard() {

    }
}
