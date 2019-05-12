package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

import java.util.ArrayList;

public class ReloadMessage extends ViewControllerMessage {
    private int messageID;
    private int weaponID;
    private int authorID;
    private PlayerView authorView;

    /**
     * ReloadMessage class's constructor
     * @param weapon
     * @param authorID
     * @param authorView
     */

    public ReloadMessage(WeaponCard weapon, int authorID, PlayerView authorView) {

        this.weaponID = weapon.getID();
        this.messageID = Identificator.RELOAD_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
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
