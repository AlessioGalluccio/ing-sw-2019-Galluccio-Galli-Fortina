package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyCardException;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static it.polimi.se2019.model.player.ColorRYB.*;


public class AmmoPowerupCard implements AmmoConvertibleCard {
    private transient AmmoDeck deck;
    private transient PowerupDeck powerupDeck;
    private ColorRYB color1;
    private ColorRYB color2;

    public AmmoPowerupCard(ColorRYB color1, ColorRYB color2, PowerupDeck powerupDeck) {
        this.color1 = color1;
        this.color2 = color2;
        this.powerupDeck = powerupDeck;
    }

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
        if(this.deck==null) this.deck = (AmmoDeck) deck;
        else throw new AlreadyDeckException("This card " + this + " has already a deck!");
    }

    /**
     *
     * @return List of ammo included in the card
     */
    @Override
    public List<ColorRYB> getAmmo() {
        ArrayList<ColorRYB> ammo = new ArrayList<>();
        ammo.add(color1);
        ammo.add(color2);
        return ammo;
    }

    /**
     * Reload a player's ammo using this card
     * @param p player to relaod
     */
    @Override
    public void reloadAmmo(Player p) {
        AmmoBag ammoPlayer = p.getAmmo();
        List<ColorRYB> ammoList = getAmmo();
        p.setAmmoBag(ammoPlayer.getRedAmmo() + Collections.frequency(ammoList, RED),
                ammoPlayer.getYellowAmmo() + Collections.frequency(ammoList, YELLOW),
                ammoPlayer.getBlueAmmo() + Collections.frequency(ammoList, BLUE));
    }

    /**
     * Discard a card
     */
    @Override
    public void discard() {
        deck.discard(this);
    }

    /**
     * Add to player's AmmoBag the new ammos and pick a powerup card
     * @param author Who use the card
     * @throws TooManyCardException if the player has already three powerup (the maximum)
     */
    @Override
    public void useCard(Player author) throws TooManyCardException {
        reloadAmmo(author);
        author.addPowerupCard(powerupDeck.pick());

    }

}
