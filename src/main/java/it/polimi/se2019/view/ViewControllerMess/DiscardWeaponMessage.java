package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;

public class DiscardWeaponMessage extends ViewControllerMessage {
    private static final long serialVersionUID = 8135867227123013091L;
    private int weaponID;

    /**
     * DiscardWeaponMessage class constructor
     * @param weaponCard the weaponCard selected
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public DiscardWeaponMessage(WeaponCard weaponCard, int authorID, View authorView) {
        this.weaponID = weaponCard.getID();
        this.messageID = Identificator.DISCARD_WEAPON_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleDiscardWeapon(weaponID);
    }
}
