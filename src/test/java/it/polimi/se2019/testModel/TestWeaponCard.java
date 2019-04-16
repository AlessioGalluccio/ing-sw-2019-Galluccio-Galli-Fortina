package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

public class TestWeaponCard {

    private WeaponDeck deck;

    @Before
    public void initTest(){
        try {
            this.deck = new WeaponDeck();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
}
