package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.WeaponCard;

import java.util.ArrayList;

public class ReloadMessage extends ViewControllerMessage {

    private ArrayList<WeaponCard> weapon;

    public ReloadMessage(ArrayList<WeaponCard> weapon) {
        this.weapon = weapon;
    }

    public ArrayList<WeaponCard> getWeapon() {
        return weapon;
    }
}
