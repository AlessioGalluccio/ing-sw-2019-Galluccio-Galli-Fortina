package it.polimi.se2019.model.deck;

import java.util.ArrayList;

public abstract class WeaponCard {

    private Color ammoGranted;
    private ArrayList<Color> ammoNotGranted;
    private String name;
    private String description;
    private Color color;
    private boolean reload;

    public WeaponCard() {

    }

    public ArrayList<Color> getBuyCost(){

    }

    public ArrayList<Color> getReloadCost() {

    }

    public boolean isReloaded(){

    }
}
