package it.polimi.se2019.model.map;

import it.polimi.se2019.model.deck.AmmoDeck;
import it.polimi.se2019.model.deck.PowerupDeck;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.deck.WeaponDeck;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCell {
    private Cell cellAmmo;
    private CellSpawn cellSpawn;

    @Before
    public void setUp() {
        Map map = new Map4(new WeaponDeck(), new AmmoDeck(new PowerupDeck()));
        map.reloadAllCell();
        cellAmmo = map.getCellByCoo(1,1);
        cellSpawn = (CellSpawn) map.getCellByCoo(0,1);
    }

    @Test
    public void testGetCardID() {
        assertEquals(1,  cellAmmo.getCardID().size());
        assertEquals(3,  cellSpawn.getCardID().size());
    }

    @Test
    public void testGrabCard() throws NotCardException {
        cellAmmo.grabCard(0);
        cellSpawn.grabCard(cellSpawn.getCardID().get(1));
        assertEquals(2,  cellSpawn.getCardID().size());
    }

    @Test (expected = NotCardException.class)
    public void testGrabCardException() throws NotCardException {
        cellSpawn.grabCard(cellSpawn.getCardID().get(0));
        cellSpawn.grabCard(cellSpawn.getCardID().get(0));
        cellSpawn.grabCard(cellSpawn.getCardID().get(0));
        cellSpawn.grabCard(10);
    }

    @Test
    public void testReplaceCard() {
        try {
            WeaponCard card = (WeaponCard) cellSpawn.grabCard(cellSpawn.getCardID().get(0));
            assertEquals(2,  cellSpawn.getCardID().size());
            cellSpawn.replaceCard(card);
            assertEquals(3,  cellSpawn.getCardID().size());
        } catch (NotCardException | TooManyException e) {
            e.printStackTrace();
        }
    }

    @Test (expected = TooManyException.class)
    public void testReplaceCardxception() throws TooManyException {
        try {
            WeaponCard card = (WeaponCard) cellSpawn.grabCard(cellSpawn.getCardID().get(0));
            assertEquals(2,  cellSpawn.getCardID().size());
            cellSpawn.replaceCard(card);
            cellSpawn.replaceCard(card);
        } catch (NotCardException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClone() {
        Player pp = new Player("PeterParker", new Character("SpiderMan", "RED"), 2016);
        pp.setPosition(cellAmmo);

        Player mj = new Player("MJ", new Character("Girl", "BLUE"), 2001);
        mj.setPosition(cellSpawn);

        Cell clone = cellAmmo.clone();
        assertEquals(cellAmmo, clone);
        assertNotSame(cellAmmo, clone);

        CellSpawn cloneSpawn = (CellSpawn) cellSpawn.clone();
        assertEquals(cellSpawn, cloneSpawn);
        assertNotSame(cellSpawn, cloneSpawn);

    }
}