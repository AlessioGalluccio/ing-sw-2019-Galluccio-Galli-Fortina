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

    }

    public ArrayList<Color> getReloadCost() {

    }

    public String getName(){

    }

    public boolean isReloaded(){

    }

    @Override
    public void useCard() {

    }
}
