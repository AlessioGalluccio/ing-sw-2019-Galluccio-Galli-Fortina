package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.AmmoDeck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testAmmoDeck {
    AmmoDeck deck;

    @Before
    public void testCostructor(){
        deck = new AmmoDeck();
    }

    @Test
    public void testSizeDeck(){
        assertEquals(36, deck.getUnusedCard().size());
    }
}
