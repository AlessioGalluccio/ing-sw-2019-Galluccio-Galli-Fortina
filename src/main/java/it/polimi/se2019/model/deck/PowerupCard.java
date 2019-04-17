package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.PlayerView;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import static it.polimi.se2019.model.player.ColorRYB.*;

public abstract class PowerupCard implements AmmoConvertibleCard {
    private transient PowerupDeck deck;
    private final ColorRYB color;
    //Due to his immutable nature, ID is not necessary

    public PowerupCard(ColorRYB color) {
        this.color = color;
    }

    /**
     * Create and send a message containing the possible targets to the view of player
     * @param player the player who wants to use this card
     * @param playerView receiver of the message
     * @return the possible target that player can hit. It return null if there is no target
     */
    public abstract ArrayList<Target> sendPossibleTarget(Player player, PlayerView playerView);

    /**
     *
     * @return Ammo that can be change for this powerup
     */
    @Override
    public List<ColorRYB> getAmmo() {
        ArrayList<ColorRYB> ammo = new ArrayList<>();
        ammo.add(color);
        return ammo;
    }

    /**
     * Set his deck for the card
     * @param deck Deck of relatives cards to set
     * @throws AlreadyDeckException If you try to reset the deck, it can change during game
     */
    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
        if(this.deck==null) this.deck = (PowerupDeck) deck;
        else throw new AlreadyDeckException("This card " + this +" has already a deck!");
    }

    /**
     * Discard a card
     */
    @Override
    public void discard() {
        deck.discard(this);
    }

    @Override
    public abstract void useCard(Player author);

    /**
     * Reload a player's ammo using this card
     * @param playerToReload player to reload
     */
    @Override
    public void reloadAmmo(Player playerToReload) {
        AmmoBag ammoPlayer = playerToReload.getAmmo();
        switch (color) {
            case BLUE:
                playerToReload.setAmmoBag(ammoPlayer.getRedAmmo(), ammoPlayer.getYellowAmmo(), ammoPlayer.getBlueAmmo() + 1);
                break;
            case YELLOW:
                playerToReload.setAmmoBag(ammoPlayer.getRedAmmo(), ammoPlayer.getYellowAmmo() + 1, ammoPlayer.getBlueAmmo());
                break;
            case RED:
                playerToReload.setAmmoBag(ammoPlayer.getRedAmmo() + 1, ammoPlayer.getYellowAmmo(), ammoPlayer.getBlueAmmo());
                break;
        }
    }
}
