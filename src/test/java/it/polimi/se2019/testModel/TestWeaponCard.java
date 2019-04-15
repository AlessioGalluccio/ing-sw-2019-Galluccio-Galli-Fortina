package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.*;
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
    public void testGetFiremode() {
        WeaponCard card = deck.pick();
        List<FireMode> list =  card.getFireMode();
        assertNotEquals(list, card.getFireMode());
    }
}
