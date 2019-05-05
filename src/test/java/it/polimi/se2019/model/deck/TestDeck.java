package it.polimi.se2019.model.deck;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestDeck {
    private WeaponDeck weaponDeck;
    private AmmoDeck ammoDeck;
    private PowerupDeck powerupDeck;
    private final int WEAPON_SIZE_DECK = 21;
    private final int AMMO_SIZE_DECK = 36;
    private final int POWERUP_SIZE_DECK = 24;

    @Before
    public void testCostructor(){
        weaponDeck = new WeaponDeck();
        powerupDeck = new PowerupDeck();
        ammoDeck = new AmmoDeck(powerupDeck);
    }

    @Test
    public void testSizeDeck(){
        assertEquals(WEAPON_SIZE_DECK, weaponDeck.getUnusedCard().size());
        assertEquals(AMMO_SIZE_DECK , ammoDeck.sizeUnususedCard());
        assertEquals(POWERUP_SIZE_DECK, powerupDeck.getUnusedCard().size());

        weaponDeck.pick();
        weaponDeck.pick();
        weaponDeck.pick();
        assertEquals(WEAPON_SIZE_DECK, weaponDeck.sizeDeck());

        for(int i=10; i>0; i--) ammoDeck.pick();
        for(int i=3 ; i>0; i--) ammoDeck.pick().discard();
        assertEquals(AMMO_SIZE_DECK , ammoDeck.sizeDeck());

    }

    @Test
    public void testPick() {
        weaponDeck.pick();
        assertEquals(1, weaponDeck.getInUseCard().size());
        assertEquals(20, weaponDeck.getUnusedCard().size());
        ammoDeck.pick();
        assertEquals(1, ammoDeck.getInUseCard().size());

        for(int i=0; i<ammoDeck.sizeDeck() + 10; i++) ammoDeck.pick().discard();
        assertEquals(AMMO_SIZE_DECK , ammoDeck.sizeDeck());

    }

    @Test(expected = AlreadyDeckException.class)
    public void testAlreadyDeckException() {
        ammoDeck.pick().setDeck(ammoDeck);
    }

    @Test
    public void testDiscard() {
        AmmoConvertibleCard acc = ammoDeck.pick();
        acc.discard();
        assertEquals(1, ammoDeck.getUsedCard().size());

        assertEquals(AMMO_SIZE_DECK , ammoDeck.getInUseCard().size() +
                ammoDeck.getUnusedCard().size() +
                ammoDeck.getUsedCard().size());

        assertTrue(acc.getAmmo().equals(ammoDeck.getUsedCard().get(0).getAmmo()));

    }
}
