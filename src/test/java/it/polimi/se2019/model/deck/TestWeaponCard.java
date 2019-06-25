package it.polimi.se2019.model.deck;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestWeaponCard {

    private WeaponDeck deck;

    @Before
    public void initTest(){
        this.deck = new WeaponDeck();
    }

    @Test
    //Testing that is a deep copy
    public void testGetFiremode() {
        WeaponCard card = deck.pick();
        List<FireMode> fireModeList =  card.getFireMode();
        assertNotEquals(fireModeList, card.getFireMode());
        assertFalse(fireModeList.equals(card.getFireMode()));
        for(FireMode f : fireModeList) {
            assertTrue(f.getID() == card.getFireMode().get(fireModeList.indexOf(f)).getID());
        }
    }

    @Test
    public void toStringTest() {
        System.out.println(deck.getCardById(1).toString());
    }
}