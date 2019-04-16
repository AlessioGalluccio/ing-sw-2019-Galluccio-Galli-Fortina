package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.PlayerView;

import java.util.ArrayList;

public abstract class PowerupCard implements AmmoConvertibleCard {
    private ColorRYB color;

    public PowerupCard(ColorRYB color) {
        this.color = color;
    }

    /**
     * create and send a message containg the possible targets to the view of player
     * @param player the player who wants to use this card
     * @return the possible target that player can hit. It return null if there is no target
     */
    public abstract ArrayList<Target> sendPossibleTarget(Player player, PlayerView playerView);

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {

    }

    @Override
    public void discard() {

    }

    @Override
    public void useCard() {

    }

    @Override
    public void reloadAmmo(Player p) {

    }
}
