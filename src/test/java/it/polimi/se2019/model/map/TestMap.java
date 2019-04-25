package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.PowerupDeck;
import it.polimi.se2019.model.deck.WeaponDeck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMap {
    Map map1;

    @Before
    public void initTest() {
        map1 = new Map1(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
    }

}