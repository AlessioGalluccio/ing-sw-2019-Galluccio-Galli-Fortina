package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyCardException;

import java.util.*;

import static it.polimi.se2019.model.player.ColorRYB.*;


public class AmmoOnlyCard implements AmmoConvertibleCard {
    private transient AmmoDeck deck;
    private ColorRYB colorSingle;
    private ColorRYB colorDouble;

    public AmmoOnlyCard(ColorRYB colorSingle, ColorRYB colorDouble) {
        this.colorSingle = colorSingle;
        this.colorDouble = colorDouble;
    }

    /**
     *
     * @return List of ammo included in the card
     */
    @Override
    public List<ColorRYB> getAmmo() {
        ArrayList<ColorRYB> ammo = new ArrayList<>();
        ammo.add(colorSingle);
        ammo.add(colorDouble);
        ammo.add(colorDouble);
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

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException{
        if(this.deck==null) this.deck = (AmmoDeck) deck;
        else throw new AlreadyDeckException("This card " + this +" has already a deck!");
    }

    @Override
    public void discard() {
        deck.discard(this);
    }

    /**
     * Add to player AmmoBag the new ammo
     * @param author Who use the card
     */
    @Override
    public void useCard(Player author) {
        reloadAmmo(author);
    }
}
