package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

public class WeaponMessage extends ViewControllerMessage {
    private static final long serialVersionUID = -835200389203068573L;
    WeaponCard weaponCard;

    /**
     * WeaponMessage constructor
     * @param weaponCard the WeaponCard selected
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public WeaponMessage(WeaponCard weaponCard, int authorID, View authorView){
        this.weaponCard = weaponCard;
        this.messageID = Identificator.WEAPON_MESSAGE;
        this.authorID = authorID;
        this.authorView = authorView;
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleWeaponCard(weaponCard);
    }
}
