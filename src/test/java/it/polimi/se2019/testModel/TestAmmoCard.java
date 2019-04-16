package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyCardException;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.se2019.model.player.ColorRYB.*;
import static org.junit.Assert.*;

public class TestAmmoCard {
    private AmmoDeck deck;
    private Player player;

    @Before
    public void initTest(){
        this.deck = new AmmoDeck(new PowerupDeck());
        this.player = new Player("Giorgio", new Character("Distruttore", "Blallo"), 1);
    }

    @Test
    public void testReload(){
        //Test half + 1 of the deck, in this way I'm sure I test both AmmoOnly and AmmoPowerup
        for(int i=0; i<deck.getUnusedCard().size() - 17; i++) {
            player.setAmmoBag(1, 1, 1);
            AmmoConvertibleCard card = deck.pick();
            card.reloadAmmo(player);

            int blue = 1;
            int red = 1;
            int yellow = 1;
            for (ColorRYB c : card.getAmmo()) {
                if (c == BLUE) blue++;
                if (c == YELLOW) yellow++;
                if (c == RED) red++;
            }
            assertEquals(red, player.getAmmo().getRedAmmo());
            assertEquals(yellow, player.getAmmo().getYellowAmmo());
            assertEquals(blue, player.getAmmo().getBlueAmmo());
        }
    }

    @Test(expected = TooManyCardException.class)
    public void testUseCard() throws TooManyCardException {
            player.addPowerupCard(new TargetingScopeCard(BLUE));
            player.addPowerupCard(new TargetingScopeCard(RED));
            player.addPowerupCard(new TargetingScopeCard(RED));

            deck.pick().useCard(player);
            deck.pick().useCard(player);
            deck.pick().useCard(player);
            deck.pick().useCard(player);
            deck.pick().useCard(player);
    }

}
