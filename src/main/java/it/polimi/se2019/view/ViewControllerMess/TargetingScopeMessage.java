package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;

public class TargetingScopeMessage extends ViewControllerMessage {
    private int messageID;
    private TargetingScopeCard usedCard;
    private ColorRYB colorAmmo;

    public TargetingScopeMessage(TargetingScopeCard usedCard, ColorRYB colorAmmo) {
        this.usedCard = usedCard;
        this.messageID = Identificator.TARGETING_SCOPE_MESSAGE;
        this.colorAmmo = colorAmmo;
    }

    /**
     * returns used TargetingScope card
     * @return the TergetingsScope card
     */
    public TargetingScopeCard getUsedCard() {
        return usedCard;
    }

    /**
     * returns the color of the ammo used
     * @return ColorRYB of the ammo used
     */
    public AmmoBag getCost(){
        switch (colorAmmo){
            case RED: return new AmmoBag(1,0,0);
            case YELLOW: return new AmmoBag(0,1,0);
            case BLUE: return new AmmoBag(0,0,1);

            //should not happen
            default: return new AmmoBag(0,0,0);
        }
    }

    @Override
    public int getMessageID() {
        return messageID;
    }

    @Override
    public void handle(StateController stateController) {
        stateController.handleTargeting(usedCard, colorAmmo);
    }
}
