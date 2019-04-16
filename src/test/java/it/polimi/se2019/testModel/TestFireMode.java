package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.player.ColorRYB;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static it.polimi.se2019.model.player.ColorRYB.RED;
import static org.junit.Assert.*;

public class TestFireMode {
    private List<FireMode> fireMode;

    @Before
    public void initTest(){
        WeaponDeck deck = null;
        try {
            deck = new WeaponDeck();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Collections.shuffle(deck.getUnusedCard());
        WeaponCard card = deck.pick();
        fireMode = card.getFireMode();
    }

    @Test
    //Testing that is a deep copy
    public void testGetCost() {
        List<ColorRYB> color1 = fireMode.get(0).getCost();
        color1.add(RED);
        List<ColorRYB> color2 = fireMode.get(0).getCost();

        assertNotEquals(color1.size(), color2.size());
        assertEquals(true, color2.equals(fireMode.get(0).getCost()));
    }
}
