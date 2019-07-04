package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.view.View;
import it.polimi.se2019.view.remoteView.PlayerView;

/**
 * message for selecting a targeting scope powerup and its ammo cost
 * @author Fortina
 * @author Galluccio
 */
public class TargetingScopeMessage extends ViewControllerMessage {

    private static final long serialVersionUID = -4381929763624561191L;
    private TargetingScopeCard usedCard;
    private ColorRYB colorAmmo;

    /**
     * TargetingScopeMessage class constructor
     * @param usedCard the TargetingScope card selected
     * @param colorAmmo the color of the ammo which must be converted in damage
     * @param authorID the ID of the author
     * @param authorView the playerView of the player
     */
    public TargetingScopeMessage(TargetingScopeCard usedCard, ColorRYB colorAmmo, int authorID, View authorView) {

        this.usedCard = usedCard;
        this.messageID = Identificator.TARGETING_SCOPE_MESSAGE;
        this.colorAmmo = colorAmmo;
        this.authorID = authorID;
        this.authorView = authorView;
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
    public static AmmoBag getCost(ColorRYB colorAmmo){
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
        stateController.handleTargeting(usedCard, getCost(colorAmmo));
    }
}
