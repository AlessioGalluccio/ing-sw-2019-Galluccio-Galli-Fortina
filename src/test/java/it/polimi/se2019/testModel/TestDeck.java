package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDeck {
    WeaponDeck weaponDeck;
    AmmoDeck ammoDeck;
    PowerupDeck powerupDeck;

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
        assertEquals(20, weaponDeck.getUnusedCard().size());}
}
