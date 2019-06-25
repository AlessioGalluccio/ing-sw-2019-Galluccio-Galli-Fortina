package it.polimi.se2019.model.player;

import it.polimi.se2019.model.deck.*;
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
    public void setUp() throws Exception {
        player = new Player("Nikola Tesla", new Character("CoilMan", "yellow"), 1856);
        enemy = new Player("Thomas Edison", new Character("LightBulbMan", "white"), 1931);
        enemy_2 = new Player("JP Morgan", new Character("MoneyMan", "green"), 1913);

        player.setAmmoBag(2,2,2);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(enemy);
        players.add(enemy_2);
        gameHandler = new GameHandler(players, 8);

    }

    @Test
    public void testAddPowerup(){
        try {
            player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 1, 0));
        } catch (TooManyException e) {
            e.printStackTrace();
        }

        assertEquals(1, player.getPowerupCardList().size());
    }

    @Test(expected = TooManyException.class)
    public void testAddPowerupException() throws TooManyException {
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 1, 0));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 2, 0));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 3, 0));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED, 4, 0));
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
            //e.printStackTrace();
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
        assertEquals(enemy, player.getMark().getMarkReceived().get(0));
        assertEquals(player, enemy.getMark().getMarkDone().get(0));
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
            if(p.equals(player)) cont++;
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

    @Test
    public void testPointCardList() {
        player.addPoints(5);
        assertEquals(player.getNumPoints(), 5);
    }

    @Test
    public void testToString() throws TooManyException, YouOverkilledException, NotPresentException, YouDeadException {
        player.receiveMarkBy(enemy);
        player.receiveMarkBy(enemy);
        player.receiveMarkBy(enemy_2);
        player.receiveDamageBy(enemy_2);
        player.receiveDamageBy(enemy_2);
        player.receiveDamageBy(enemy_2);
        player.receiveDamageBy(enemy);
        player.addPowerupCard(new TeleporterCard(ColorRYB.BLUE, 0,0));
        player.addPowerupCard(new NewtonCard(ColorRYB.RED, 0,0));
        System.out.print(player.toString());
    }

    @Test (expected = TooManyException.class)
    public void addWeaponTest() throws TooManyException {
        player.addWeaponCard(gameHandler.getWeaponCardByID(1));
        player.addWeaponCard(gameHandler.getWeaponCardByID(2));
        player.addWeaponCard(gameHandler.getWeaponCardByID(3));
        assertEquals(3, player.getWeaponCardList().size());
        player.addWeaponCard(gameHandler.getWeaponCardByID(4));
        assertEquals(3, player.getWeaponCardList().size());
    }

    @Test
    public void loadWeapon() throws TooManyException, NotEnoughAmmoException, NotPresentException, WeaponIsLoadedException {
        WeaponCard weapon = gameHandler.getWeaponCardByID(4);
        player.addWeaponCard(weapon);
        weapon.unload();
        player.loadWeapon(4);
        assertEquals(1, player.getAmmo().getYellowAmmo());
        assertEquals(0, player.getAmmo().getRedAmmo());
        assertEquals(2, player.getAmmo().getBlueAmmo());
    }

    @Test (expected = WeaponIsLoadedException.class)
    public void loadWeaponReloaded() throws TooManyException, NotEnoughAmmoException, NotPresentException, WeaponIsLoadedException {
        player.addWeaponCard(gameHandler.getWeaponCardByID(4));
        player.loadWeapon(4);
    }

    @Test (expected = NotPresentException.class)
    public void loadWeaponNotPresent() throws TooManyException, NotEnoughAmmoException, NotPresentException, WeaponIsLoadedException {
        player.addWeaponCard(gameHandler.getWeaponCardByID(4));
        player.loadWeapon(7);
    }

    @Test (expected = NotEnoughAmmoException.class)
    public void loadWeaponNotAmmo() throws TooManyException, NotPresentException, WeaponIsLoadedException, NotEnoughAmmoException {
        WeaponCard weapon = gameHandler.getWeaponCardByID(4);
        player.addWeaponCard(weapon);
        weapon.unload();
        player.setAmmoBag(1,1,2);
        try {
            player.loadWeapon(4);
        } catch (NotEnoughAmmoException e) {
            //test ammo not changed
            assertEquals(1, player.getAmmo().getYellowAmmo());
            assertEquals(1, player.getAmmo().getRedAmmo());
            assertEquals(2, player.getAmmo().getBlueAmmo());
            throw new NotEnoughAmmoException();
        }
    }

    @Test
    public void discardPowerup() throws Exception {
        PowerupCard card_1 = gameHandler.getPowerupCardByID(1);
        PowerupCard card_2 = gameHandler.getPowerupCardByID(2);

        player.setAmmoBag(0,0,0);

        player.addPowerupCard(card_2);
        player.addPowerupCard(card_1);
        player.discardCard(card_1, true);
        assertFalse(player.getPowerupCardList().contains(card_1));
        assertTrue(player.getPowerupCardList().contains(card_2));
        switch (card_1.getColor()) {
            case "BLUE":
                assertFalse(player.canPayAmmo(new AmmoBag(0,0,1)));
                break;
            case "RED":
                assertFalse(player.canPayAmmo(new AmmoBag(1,0,0)));
                break;
            case "YELLOW":
                assertFalse(player.canPayAmmo(new AmmoBag(0,1,0)));
                break;
        }

        player.addPowerupCard(card_1);
        player.discardCard(card_1, false);
        assertFalse(player.getPowerupCardList().contains(card_1));
        assertTrue(player.getPowerupCardList().contains(card_2));
        switch (card_1.getColor()) {
            case "BLUE":
                assertTrue(player.canPayAmmo(new AmmoBag(0,0,1)));
                break;
            case "RED":
                assertTrue(player.canPayAmmo(new AmmoBag(1,0,0)));
                break;
            case "YELLOW":
                assertTrue(player.canPayAmmo(new AmmoBag(0,1,0)));
                break;
        }
    }

    @Test (expected = NotPresentException.class)
    public void discardPowerupNotPresent() throws Exception {
        PowerupCard card_1 = gameHandler.getPowerupCardByID(1);
        PowerupCard card_2 = gameHandler.getPowerupCardByID(2);
        PowerupCard card_3 = gameHandler.getPowerupCardByID(3);

        player.setAmmoBag(0,0,0);

        player.addPowerupCard(card_1);
        player.addPowerupCard(card_2);
        player.discardCard(card_3, true);
    }

}