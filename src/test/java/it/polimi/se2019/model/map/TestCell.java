package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.PowerupDeck;
import it.polimi.se2019.model.deck.WeaponDeck;
import org.junit.Before;

import static org.junit.Assert.*;

public class TestCell {
    Cell cell1;

    @Before
    public void setUp() {
        Map map = new Map1(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
        cell1 = map.getCellByCoo(1,1);
    }
}