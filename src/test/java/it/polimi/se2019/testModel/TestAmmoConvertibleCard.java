package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.se2019.model.player.ColorRYB.*;
import static org.junit.Assert.*;

public class TestAmmoConvertibleCard {
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
        try {
            for (int i = 0; i < deck.getUnusedCard().size() - 17; i++) {
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

                player.setAmmoBag(1, 1, 1);
                new TargetingScopeCard(RED).reloadAmmo(player);
                assertEquals(2, player.getAmmo().getRedAmmo());
            }
        }
        catch (TooManyException e ) {fail();}
    }

    @Test(expected = TooManyException.class)
    public void testUseCardException() throws TooManyException {
            player.addPowerupCard(new TargetingScopeCard(BLUE));
            player.addPowerupCard(new TargetingScopeCard(RED));
            player.addPowerupCard(new TargetingScopeCard(RED));

            //Reset ammoBag to 0 in order to don't thrown TooManyException due to ammo
            player.setAmmoBag(0,0,0);
            deck.pick().useCard(player);
            player.setAmmoBag(0,0,0);
            deck.pick().useCard(player);
            player.setAmmoBag(0,0,0);
            deck.pick().useCard(player);
            player.setAmmoBag(0,0,0);
            deck.pick().useCard(player);
            player.setAmmoBag(0,0,0);
            deck.pick().useCard(player);
    }

}
