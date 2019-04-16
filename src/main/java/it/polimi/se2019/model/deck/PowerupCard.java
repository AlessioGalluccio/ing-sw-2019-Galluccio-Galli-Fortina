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
     * create and send a message containg the possible targets to the view of player
     * @param player the player who wants to use this card
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

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
        if(this.deck==null) this.deck = (PowerupDeck) deck;
        else throw new AlreadyDeckException("This card " + this +" has already a deck!");
    }

    @Override
    public void discard() {
        deck.discard(this);
    }

    @Override
    public abstract void useCard(Player author);

    /**
     * Reload a player's ammo using this card
     * @param p player to relaod
     */
    @Override
    public void reloadAmmo(Player p) {
        AmmoBag ammoPlayer = p.getAmmo();
        switch (color) {
            case BLUE:
                p.setAmmoBag(ammoPlayer.getRedAmmo(), ammoPlayer.getYellowAmmo(), ammoPlayer.getBlueAmmo() + 1);
                break;
            case YELLOW:
                p.setAmmoBag(ammoPlayer.getRedAmmo(), ammoPlayer.getYellowAmmo() + 1, ammoPlayer.getBlueAmmo());
                break;
            case RED:
                p.setAmmoBag(ammoPlayer.getRedAmmo() + 1, ammoPlayer.getYellowAmmo(), ammoPlayer.getBlueAmmo());
                break;
        }
    }
}
