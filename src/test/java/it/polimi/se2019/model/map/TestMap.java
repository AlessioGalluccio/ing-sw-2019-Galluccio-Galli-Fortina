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
    Cell[][] cell;

    @Before
    public void initTest() {
        map1 = new Map1(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
        cell = map1.getCell();
    }

    @Test
    public void testGetCellInDirection() {
        List<Cell> cells = new ArrayList<>();

        cells.add(cell[1][1]);
        cells.add(cell[1][2]);
        assertEquals(cells, map1.getCellInDirection(cell[1][1], 'N'));

        cells.clear();
        cells.add(cell[1][1]);
        cells.add(cell[1][0]);
        assertEquals(cells, map1.getCellInDirection(cell[1][1], 'S'));

        cells.clear();
        cells.add(cell[1][1]);
        cells.add(cell[2][1]);
        cells.add(cell[3][1]);
        assertEquals(cells, map1.getCellInDirection(cell[1][1], 'e'));

        cells.clear();
        cells.add(cell[1][1]);
        cells.add(cell[0][1]);
        assertEquals(cells, map1.getCellInDirection(cell[1][1], 'W'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellInDirectionException() throws IllegalArgumentException {
        map1.getCellInDirection(cell[1][1], 'z');
    }

    @Test
    public void testGetCellAtDistance() {
        List<Cell> cells = new ArrayList<>();

        cells.add(cell[1][1]);
        assertEquals(cells, map1.getCellAtDistance(cell[1][1], 0));

        cells.add(cell[2][1]);
        cells.add(cell[3][1]);
        cells.add(cell[2][2]);
        cells.add(cell[1][0]);
        cells.add(cell[0][1]);
        cells.add(cell[0][2]);
        cells.add(cell[2][0]);
        assertTrue(map1.getCellAtDistance(cell[1][1], 2).containsAll(cells));
        assertTrue(cells.containsAll(map1.getCellAtDistance(cell[1][1], 2)));

        cells.add(cell[1][2]);
        cells.add(cell[3][0]);
        assertTrue(map1.getCellAtDistance(cell[1][1], 3).containsAll(cells));
        assertTrue(cells.containsAll(map1.getCellAtDistance(cell[1][1], 3)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellAtDistanceException() throws IllegalArgumentException {
        map1.getCellAtDistance(cell[1][1], -2);
    }
}