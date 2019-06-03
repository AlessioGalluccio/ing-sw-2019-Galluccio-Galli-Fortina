package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.remoteView.PlayerView;

public class WeaponMessage extends ViewControllerMessage {
    WeaponCard weaponCard;

    public WeaponMessage(WeaponCard weaponCard, int authorID, PlayerView authorView){
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
