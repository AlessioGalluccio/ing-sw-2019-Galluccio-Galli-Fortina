package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.PowerupDeck;
import it.polimi.se2019.model.deck.WeaponDeck;

import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMap {
    Map map1;
    Map map2;
    Map map3;
    Map map4;
    Cell[][] cellMap1;

    @Before
    public void initTest() {
        map1 = new Map1(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
        cellMap1 = map1.getCell();

        map2 = new Map2(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
        map3 = new Map3(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
        map4 = new Map4(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
    }

    @Test
    public void testGetCellInDirection() {
        List<Cell> cells = new ArrayList<>();

        cells.add(cellMap1[1][1]);
        cells.add(cellMap1[1][2]);
        assertEquals(cells, map1.getCellInDirection(cellMap1[1][1], 'N'));

        cells.clear();
        cells.add(cellMap1[1][1]);
        cells.add(cellMap1[1][0]);
        assertEquals(cells, map1.getCellInDirection(cellMap1[1][1], 'S'));

        cells.clear();
        cells.add(cellMap1[1][1]);
        cells.add(cellMap1[2][1]);
        cells.add(cellMap1[3][1]);
        assertEquals(cells, map1.getCellInDirection(cellMap1[1][1], 'e'));

        cells.clear();
        cells.add(cellMap1[1][1]);
        cells.add(cellMap1[0][1]);
        assertEquals(cells, map1.getCellInDirection(cellMap1[1][1], 'W'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellInDirectionException() throws IllegalArgumentException {
        map1.getCellInDirection(cellMap1[1][1], 'z');
    }

    @Test
    public void testGetCellAtDistance() {
        List<Cell> cells = new ArrayList<>();

        cells.add(cellMap1[1][1]);
        assertEquals(cells, map1.getCellAtDistance(cellMap1[1][1], 0));

        cells.add(cellMap1[1][0]);
        cells.add(cellMap1[0][1]);
        cells.add(cellMap1[0][2]);
        cells.add(cellMap1[2][0]);
        assertTrue(map1.getCellAtDistance(cellMap1[1][1], 2).containsAll(cells));
        assertTrue(cells.containsAll(map1.getCellAtDistance(cellMap1[1][1], 2)));

        cells.add(cellMap1[1][2]);
        cells.add(cellMap1[3][0]);
        cells.add(cellMap1[2][1]);
        assertTrue(map1.getCellAtDistance(cellMap1[1][1], 3).containsAll(cells));
        assertTrue(cells.containsAll(map1.getCellAtDistance(cellMap1[1][1], 3)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCellAtDistanceException() throws IllegalArgumentException {
        map1.getCellAtDistance(cellMap1[1][1], -2);
    }

    @Test
    public void testGetDistance() {
        assertEquals(0, map1.getDistance(cellMap1[1][1], cellMap1[1][1]));
        assertEquals(1, map1.getDistance(cellMap1[1][1], cellMap1[0][1]));
        assertEquals(2, map1.getDistance(cellMap1[1][1], cellMap1[0][2]));
        assertEquals(3, map1.getDistance(cellMap1[1][1], cellMap1[3][0]));
        assertEquals(3, map1.getDistance(cellMap1[1][1], cellMap1[1][2]));
    }

    @Test
    public void testClone() {
        Map clone1 = map1.clone();

        assertNotSame(map1, clone1);
        assertEquals(map1.getID(), clone1.getID());

        Cell[][] cellClone = clone1.getCell();

        for(int i = 0; i< cellMap1.length; i++) {
            for(int j = 0; j< cellMap1[i].length; j++) {
                if(cellMap1[i][j]!=null) {
                    assertEquals(cellMap1[i][j], cellClone[i][j]);
                    assertNotSame(cellMap1[i][j], cellClone[i][j]);
                }
            }
        }
    }

    @Test
    public void tesCanSee() {
        assertTrue(map1.canSee(cellMap1[1][1], cellMap1[1][1]));
        assertTrue(map1.canSee(cellMap1[0][1], cellMap1[1][1]));
        assertTrue(map1.canSee(cellMap1[1][1], cellMap1[1][0]));
        assertFalse(map1.canSee(cellMap1[1][1], cellMap1[2][1]));
        assertFalse(map1.canSee(cellMap1[1][1], cellMap1[2][2]));

        assertTrue(map1.canSee(cellMap1[3][2], cellMap1[2][1]));
        assertTrue(map1.canSee(cellMap1[3][2], cellMap1[0][2]));
        assertFalse(map1.canSee(cellMap1[3][2], cellMap1[0][1]));
    }
}