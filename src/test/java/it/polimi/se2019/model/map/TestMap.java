package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.PowerupDeck;
import it.polimi.se2019.model.deck.WeaponDeck;
import java.util.List;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMap {
    Map map1;

    @Before
    public void initTest() {
        map1 = new Map1(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
    }

    @Test
    public void testGetCellInDirection() {
        Cell[][] map = map1.getCell();
        List<Cell> cells = new ArrayList<>();

        cells.add(map[1][1]);
        cells.add(map[1][2]);
        assertEquals(cells, map1.getCellInDirection(map[1][1], 'N'));

        cells.clear();
        cells.add(map[1][1]);
        cells.add(map[1][0]);
        assertEquals(cells, map1.getCellInDirection(map[1][1], 'S'));

        cells.clear();
        cells.add(map[1][1]);
        cells.add(map[2][1]);
        cells.add(map[3][1]);
        assertEquals(cells, map1.getCellInDirection(map[1][1], 'e'));

        cells.clear();
        cells.add(map[1][1]);
        cells.add(map[0][1]);
        assertEquals(cells, map1.getCellInDirection(map[1][1], 'W'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellInDirectionException() throws IllegalArgumentException {
        Cell[][] map = map1.getCell();
        map1.getCellInDirection(map[1][1], 'z');
    }
}