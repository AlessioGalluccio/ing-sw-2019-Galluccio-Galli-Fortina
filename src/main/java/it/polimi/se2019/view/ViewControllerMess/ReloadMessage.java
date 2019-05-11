package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.Identificator;

import java.util.ArrayList;

public class ReloadMessage extends ViewControllerMessage {
    private int messageID;
    private int weaponID;

    public ReloadMessage(WeaponCard weapon) {
        this.weaponID = weapon.getID();
        this.messageID = Identificator.RELOAD_MESSAGE;
    }

    public int getWeaponID() {
        return weaponID;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleReload(weaponID);
    }
}
