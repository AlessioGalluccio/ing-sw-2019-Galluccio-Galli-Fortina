package it.polimi.se2019.model.player;

import it.polimi.se2019.model.deck.PointCard;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestPlayer {
    private Player player;
    private Player enemy;
    private Player enemy_2;
    private GameHandler gameHandler;
    private int MAX_AMMO = 3;

    @Before
    public void initTest() {
        player = new Player("Nikola Tesla", new Character("CoilMan", "yellow"), 1856);
        enemy = new Player("Thomas Edison", new Character("LightBulbMan", "white"), 1931);
        enemy_2 = new Player("JP Morgan", new Character("MoneyMan", "green"), 1913);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(enemy);
        players.add(enemy_2);
        gameHandler = new GameHandler(players, 8);

    }

    @Test
    public void testAddPowerup(){
        try {
            player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 1));
        } catch (TooManyException e) {
            e.printStackTrace();
        }

        assertEquals(1, player.getPowerupCardList().size());
    }

    @Test(expected = TooManyException.class)
    public void testAddPowerupException() throws TooManyException {
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 1));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 2));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 3));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 4));
    }

    @Test
    public void testAddPoints() {
        player.addPoints(20);
        assertEquals(20, player.getNumPoints());

        List<PointCard> pointCards = player.getPointCardList();
        for(PointCard c : pointCards) {
            assertEquals(4, c.getValue());
        }

        player.addPoints(2);
        assertEquals(22, player.getNumPoints());

        pointCards = player.getPointCardList();
        assertEquals(4, pointCards.get(0).getValue());
        assertEquals(4, pointCards.get(4).getValue());
        assertEquals(2, pointCards.get(5).getValue());
    }

    @Test(expected = TooManyException.class)
    public void testSetAmmoException() throws TooManyException {
        player.setAmmoBag(4,2,1);
    }

    @Test
    public void testSetAmmo() {
        try {
            player.setAmmoBag(4,5,8);
        } catch (TooManyException e) {
            e.printStackTrace();
        }
        finally {
            assertEquals(MAX_AMMO, player.getAmmo().getBlueAmmo());
            assertEquals(MAX_AMMO, player.getAmmo().getRedAmmo());
            assertEquals(MAX_AMMO, player.getAmmo().getYellowAmmo());
        }
    }

    @Test
    public void testReceiveMark() {
        try {
            player.receiveMarkBy(enemy);
        } catch (TooManyException e) {
            e.printStackTrace();
        }
        assertEquals(enemy.getID(), player.getMark().getMarkReceived().get(0).getID());
        assertEquals(player.getID(), enemy.getMark().getMarkDone().get(0).getID());
    }

    @Test
    public void testRemoveReceivedMark() throws TooManyException {
        player.receiveMarkBy(enemy);
        player.receiveMarkBy(enemy);
        player.receiveMarkBy(enemy_2);
        player.receiveMarkBy(enemy);

        //Assert enemy have successfully marked three time player
        List<Player> list = player.getMark().getMarkReceived();
        int cont = 0;
        for(Player p  : list) {
            if(p.getID() == enemy.getID()) cont++;
        }
        assertEquals(3, cont);
        list = enemy.getMark().getMarkDone();
        cont = 0;
        for(Player p  : list) {
            if(p.getID() == player.getID()) cont++;
        }
        assertEquals(3, cont);

        //Now remove those mark
        player.removeMarkReceivedBy(enemy);

        list = player.getMark().getMarkReceived();
        cont = 0;
        for(Player p  : list) {
            if(p.getID() == enemy.getID()) cont++;
        }
        assertEquals(0, cont);

        list = enemy.getMark().getMarkDone();
        cont = 0;
        for(Player p  : list) {
            if(p.getID() == player.getID()) cont++;
        }
        assertEquals(0, cont);

        //Assert marks made my enemy_2 were not removed
        list = player.getMark().getMarkReceived();
        cont = 0;
        for(Player p  : list) {
            if(p.getID() == enemy_2.getID()) cont++;
        }
        assertEquals(1, cont);
    }

    @Test
    public void testSetCell() {
        Cell cell1 = new CellAmmo(new Wall(), new Passage(), new Door(), new Wall(), 0, 1, null);
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(cell1);
        Room room = new Room(null, "NotAColor", cells);
        cells.clear();
        player.setPosition(cell1);


        Cell cell2 = new CellAmmo(new Passage(), new Passage(), new Door(), new Wall(), 1, 0, null);
        cells = new ArrayList<>();
        cells.add(cell2);
        Room room2 = new Room(null, "NeitherAColor", cells);
        player.setPosition(cell2);

        assertEquals(cell2.getCoordinateX(), player.getCell().getCoordinateX());
        assertEquals(cell2.getCoordinateY(), player.getCell().getCoordinateY());
        assertTrue(cell2.getPlayerHere().contains(player));
        assertTrue(cell2.getRoom().getPlayerHere().contains(player));
        assertTrue(cell2.getPlayerHere().contains(player));
        assertFalse(cell1.getPlayerHere().contains(player));
    }

    @Test (expected = YouDeadException.class)
    public void testReceiveDamageByEx1() throws YouDeadException{
        try {
            for(int i=0; i<11; i++) player.receiveDamageBy(enemy);
        } catch (YouOverkilledException | NotPresentException e) {
            e.printStackTrace();
        }
    }

    @Test (expected = YouOverkilledException.class)
    public void testReceiveDamageByEx2() throws YouOverkilledException, NotPresentException {
        try {
            for(int i=0; i<12; i++) player.receiveDamageBy(enemy);
        } catch (YouDeadException e) {
            try {
                player.receiveDamageBy(enemy_2);
            } catch (YouDeadException ex) {

            }
        }
    }

    @Test (expected = NotPresentException.class)
    public void testReceiveDamageByEx3() throws NotPresentException {
        try {
            for(int i=0; i<11; i++) player.receiveDamageBy(enemy);
        } catch (YouDeadException | YouOverkilledException e) {
            try {
                player.receiveDamageBy(enemy_2);
            } catch (YouDeadException | YouOverkilledException ex) {
                try {
                    player.receiveDamageBy(enemy_2);
                } catch (YouDeadException | YouOverkilledException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testClone() throws TooManyException, YouOverkilledException, NotPresentException, YouDeadException {
        player.setAmmoBag(1,0,3);
        player.addPoints(18);
        player.receiveMarkBy(enemy);
        player.receiveDamageBy(enemy);
        player.receiveDamageBy(enemy_2);
        enemy_2.receiveDamageBy(player);
        enemy.receiveMarkBy(player);

        Player clone = player.clone();
        assertNotSame(player, clone);
        assertEquals(player, clone);
        assertNotSame(player.getAmmo(), clone.getAmmo());
        assertEquals(player.getAmmo().getBlueAmmo(), clone.getAmmo().getBlueAmmo());
        assertEquals(player.getAmmo().getRedAmmo(), clone.getAmmo().getRedAmmo());
        assertEquals(player.getAmmo().getYellowAmmo(), clone.getAmmo().getYellowAmmo());
        assertEquals(player.getMark().getMarkReceived().get(0), (clone.getMark().getMarkReceived().get(0)));
        assertEquals(player.getNumPoints(), clone.getNumPoints());
    }

}