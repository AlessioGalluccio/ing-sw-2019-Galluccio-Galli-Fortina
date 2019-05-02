package it.polimi.se2019.model.map;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    private Room room;

    @Before
    public void setUp() {
        ArrayList<Cell> cells = new ArrayList<>();
        Cell cell1 = new CellAmmo(new Wall(), new Passage(), new Door(), new Wall(), 0, 0, null);
        Cell cell2 = new CellAmmo(new Door(), new Passage(), new Door(), new Wall(), 1, 0, null);
        Cell cell3 = new CellSpawn(new Wall(), new Wall(), new Door(), new Wall(), 2, 0, null);

        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        room = new Room((CellSpawn) cell3, "BLUE", cells);
    }

    @Test
    public void testEquals() {
        ArrayList<Cell> cells = new ArrayList<>();
        Cell cell1 = new CellAmmo(new Wall(), new Passage(), new Door(), new Wall(), 0, 0, null);
        Cell cell2 = new CellAmmo(new Door(), new Passage(), new Door(), new Wall(), 1, 0, null);
        Cell cell3 = new CellSpawn(new Wall(), new Wall(), new Door(), new Wall(), 2, 0, null);

        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        Room room2 = new Room((CellSpawn) cell3, "BLUE", cells);

        cells.clear();
        cell3 = new CellSpawn(new Passage(), new Wall(), new Door(), new Wall(), 3, 1, null);
        cells.add(cell3);
        Room room3 = new Room((CellSpawn) cell3, "RED", cells);

        assertTrue(room.equals(room2));
        assertTrue(room.equals(room));
        assertFalse(room.equals(new Object()));
        assertFalse(room.equals(null));
        assertFalse(room.equals(room3));
    }
}