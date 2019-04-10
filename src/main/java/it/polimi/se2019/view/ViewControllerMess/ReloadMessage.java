package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.Identificator;

import java.util.ArrayList;

public class ReloadMessage extends ViewControllerMessage {
    private int messageID;
    private ArrayList<WeaponCard> weapon;

    public ReloadMessage(ArrayList<WeaponCard> weapon) {
        this.weapon = weapon;
        this.messageID = Identificator.RELOAD_MESSAGE;
    }

    public ArrayList<WeaponCard> getWeapon() {
        return weapon;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }
}
