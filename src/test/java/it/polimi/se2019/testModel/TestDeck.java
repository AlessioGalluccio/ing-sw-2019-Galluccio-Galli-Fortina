package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDeck {
    private WeaponDeck weaponDeck;
    private AmmoDeck ammoDeck;
    private PowerupDeck powerupDeck;

    @Before
    public void testCostructor(){
        weaponDeck = new WeaponDeck();
        ammoDeck = new AmmoDeck();
        powerupDeck = new PowerupDeck();
    }

    @Test
    public void testSizeDeck(){
        assertEquals(21, weaponDeck.getUnusedCard().size());
        assertEquals(36, ammoDeck.getUnusedCard().size());
        assertEquals(24, powerupDeck.getUnusedCard().size());
    }

    @Test
    public void testPick() {
        weaponDeck.pick();
        assertEquals(1, weaponDeck.getInUseCard().size());
        assertEquals(20, weaponDeck.getUnusedCard().size());
        ammoDeck.pick();
        assertEquals(1, ammoDeck.getInUseCard().size());
    }

    @Test(expected = AlreadyDeckException.class)
    public void testAlreadyDeckException() {
        ammoDeck.pick().setDeck(ammoDeck);
    }

    @Test
    public void testDiscard() {
        AmmoConvertibleCard acc = ammoDeck.pick();;
        for(int i=0; i<15; i++) {
            acc = ammoDeck.pick();
        }
        acc.discard();
        assertEquals(1, ammoDeck.getUsedCard().size());
        assertEquals(36, ammoDeck.getInUseCard().size() +
                        ammoDeck.getUnusedCard().size() +
                ammoDeck.getUsedCard().size());
        assertEquals(acc, ammoDeck.getUsedCard().get(0));

    }
}
