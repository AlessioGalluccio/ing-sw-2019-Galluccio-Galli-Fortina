package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.deck.firemodes.ElectroScythe_1;
import it.polimi.se2019.model.deck.firemodes.ShockWave_2;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameHandlerTest {

    private GameHandler gameHandler;
    private int FIRST_ID = 1;
    private int SECOND_ID = 2;
    private int THIRD_ID = 3;

    @Before
    public void setUp() {
        Player firstPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), FIRST_ID);
        Player secondPlayer = new Player("SteveRogers", new Character("CapAmerica", "blue"), SECOND_ID);
        Player thirdPlayer = new Player("PeterParker", new Character("SpiderMan", "red"), THIRD_ID);
        Player fourthPlayer = new Player("CarolDenvers", new Character("CapMarvel", "white"), 4);

        ArrayList<Player> players = new ArrayList<>();
        players.add(firstPlayer);
        players.add(secondPlayer);
        players.add(thirdPlayer);
        players.add(fourthPlayer);
        gameHandler = new GameHandler(players);
    }

    @Test
    public void isFinished() {
    }

    @Test
    public void nextTurn() {
    }

    @Test
    public void finishGame() {
    }

    @Test
    public void getTurnPlayerID() {
        int ID = gameHandler.getTurnPlayerID();
        assertEquals(FIRST_ID,ID);
        gameHandler.nextTurn();
        ID = gameHandler.getTurnPlayerID();
        assertEquals(SECOND_ID,ID);
    }

    @Test
    public void getPlayerByID() throws NoSuchPlayerException {
        Player playerObtained = gameHandler.getPlayerByID(FIRST_ID);
        assertEquals("TonyStark", playerObtained.getNickname());
        playerObtained = gameHandler.getPlayerByID(SECOND_ID);
        assertEquals("SteveRogers", playerObtained.getNickname());
    }

    @Test
    public void getCellByCoordinate() {
    }

    @Test
    public void getRoomByID() {

    }

    @Test
    public void getActionByID() {
    }

    @Test
    public void getOrderPlayerList() {
        List<Player> list = gameHandler.getOrderPlayerList();
        assertEquals("TonyStark", list.get(0).getNickname());
    }

    @Test
    public void getFireModeByID() {
        assertEquals(ElectroScythe_1.class, gameHandler.getFireModeByID(11).getClass());
        assertEquals(ShockWave_2.class, gameHandler.getFireModeByID(122).getClass());
        assertNull(gameHandler.getFireModeByID(7));
    }


    @Test
    public void cashPoint() throws NotPresentException {
        Player e1 = gameHandler.getPlayerByID(1);
        Player p = gameHandler.getPlayerByID(2);
        Player e2 = gameHandler.getPlayerByID(3);
        Player e3 = gameHandler.getPlayerByID(4);

        //test double kill and draw
        try {
            for(int i=0; i<3; i++) {
                p.receiveDamageBy(e2);
                e3.receiveDamageBy(e2);
            }
            for(int i=0; i<3; i++) {
                p.receiveDamageBy(e1);
                e3.receiveDamageBy(e1);
            }
            for(int i=0; i<3; i++) {
                p.receiveDamageBy(e3);
                e3.receiveDamageBy(p);
            }
            for(int i=0; i<6; i++) {
                p.receiveDamageBy(e1);
                e3.receiveDamageBy(e1);
            }
        } catch (YouDeadException | YouOverkilledException e) {
            try {
                p.receiveDamageBy(e1); //the kill shot
            } catch (YouDeadException | YouOverkilledException ex) {
                try {
                    e3.receiveDamageBy(e1);
                } catch (YouDeadException | YouOverkilledException exc) {
                    gameHandler.checkDeath();
                    assertEquals(4, p.getNumPoints());
                    assertEquals(14, e2.getNumPoints());
                    assertEquals(20, e1.getNumPoints());
                    assertEquals(4, e3.getNumPoints());
                    assertEquals(0, p.getDamage().size());
                    assertEquals(0, e3.getDamage().size());
                }
            }
        }

        setUp();
        e1 = gameHandler.getPlayerByID(1);
        p = gameHandler.getPlayerByID(2);
        e2 = gameHandler.getPlayerByID(3);
        e3 = gameHandler.getPlayerByID(4);
        //test skull != 1
        try {
            p.resurrection(null);
            for(int i=0; i<3; i++) p.receiveDamageBy(e2);
            for(int i=0; i<3; i++) p.receiveDamageBy(e1);
            for(int i=0; i<3; i++) p.receiveDamageBy(e3);
            for(int i=0; i<6; i++) p.receiveDamageBy(e1);
        } catch (YouDeadException | YouOverkilledException e) {
            try {
                p.receiveDamageBy(e1); //the kill shot
            } catch (YouDeadException | YouOverkilledException ex) {
                gameHandler.checkDeath();
                assertEquals(0, p.getNumPoints());
                assertEquals(5, e2.getNumPoints());
                assertEquals(8, e1.getNumPoints());
                assertEquals(2, e3.getNumPoints());
            }
        }

        setUp();
        e1 = gameHandler.getPlayerByID(1);
        p = gameHandler.getPlayerByID(2);
        e2 = gameHandler.getPlayerByID(3);
        e3 = gameHandler.getPlayerByID(4);
        //test isFrenzyDeath
        try {
            p.setFrenzyDeath();
            for(int i=0; i<3; i++) p.receiveDamageBy(e2);
            for(int i=0; i<3; i++) p.receiveDamageBy(e1);
            for(int i=0; i<3; i++) p.receiveDamageBy(e3);
            for(int i=0; i<6; i++) p.receiveDamageBy(e1);
        } catch (YouDeadException | YouOverkilledException e) {
            try {
                p.receiveDamageBy(e1); //the kill shot
            } catch (YouDeadException | YouOverkilledException ex) {
                gameHandler.checkDeath();
                assertEquals(0, p.getNumPoints());
                assertEquals(2, e2.getNumPoints());
                assertEquals(4, e1.getNumPoints());
                assertEquals(1, e3.getNumPoints());
                assertEquals(0, p.getDamage().size());
            }
        }
    }
}