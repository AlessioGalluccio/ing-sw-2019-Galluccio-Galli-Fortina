package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public class ReloadMessage extends ViewControllerMessage {

    private static final long serialVersionUID = -5035315248432532602L;
    private int weaponID;

    /**
     * ReloadMessage class constructor
     * @param weapon the WeaponCard selected for reloading
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public ReloadMessage(WeaponCard weapon, int authorID, View authorView) {

        this.weaponID = weapon.getID();
        this.messageID = Identificator.RELOAD_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    /**
     * get the ID of the weapon selected
     * @return the int ID of the weapon selected
     */
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
