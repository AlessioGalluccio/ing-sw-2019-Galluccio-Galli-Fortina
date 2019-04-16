package it.polimi.se2019.model.deck;

import it.polimi.se2019.model.player.Color;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.PlayerView;

import java.util.ArrayList;

import java.util.List;

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

    @Override
    public List<ColorRYB> getAmmo() {
        return null; //TODO
    }

    @Override
    public void setDeck(Deck deck) throws AlreadyDeckException {
        if(this.deck==null) this.deck = (PowerupDeck) deck;
        else throw new AlreadyDeckException("This card " + this +" has already a deck!");
    }

    @Override
    public void discard() {

    }

    @Override
    public void useCard(Player author) {

    }

    @Override
    public void reloadAmmo(Player p) {

    }
}
