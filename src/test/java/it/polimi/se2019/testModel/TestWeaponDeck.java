package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestWeaponDeck {
WeaponDeck deck;

    @Before
    public void testCostructor(){
        deck = new WeaponDeck();
    }

    @Test
    public void testSizeDeck(){
        assertEquals(21, deck.getUnusedCard().size());
    }
}
