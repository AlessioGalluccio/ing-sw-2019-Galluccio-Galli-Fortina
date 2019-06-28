package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.model.deck.firemodes.ElectroScythe_1;
import it.polimi.se2019.model.deck.firemodes.ShockWave_2;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellAmmo;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.map.Room;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameHandlerTest {

    private GameHandler gameHandler;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player thirdPlayer;
    private Player fourthPlayer;
    private int FIRST_ID = 1;
    private int SECOND_ID = 2;
    private int THIRD_ID = 3;

    @Before
    public void setUp() {
        firstPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), FIRST_ID);
        secondPlayer = new Player("SteveRogers", new Character("CapAmerica", "blue"), SECOND_ID);
        thirdPlayer = new Player("PeterParker", new Character("SpiderMan", "red"), THIRD_ID);
        fourthPlayer = new Player("CarolDenvers", new Character("CapMarvel", "white"), 4);

        gameHandler = new GameHandler(99);

        Server serverMock = mock(Server.class);

        PlayerView firstPlayerView = new PlayerView(serverMock, firstPlayer);
        PlayerView secondPlayerView = new PlayerView(serverMock, secondPlayer);
        PlayerView thirdPlayerView = new PlayerView(serverMock, thirdPlayer);
        PlayerView fourthPlayerView = new PlayerView(serverMock, fourthPlayer);
        gameHandler.setUp(firstPlayer, firstPlayerView, new Controller(gameHandler, firstPlayer, firstPlayerView));
        gameHandler.setUp(secondPlayer, secondPlayerView, new Controller(gameHandler, secondPlayer, secondPlayerView));
        gameHandler.setUp(thirdPlayer, thirdPlayerView, new Controller(gameHandler, thirdPlayer, thirdPlayerView));
        gameHandler.setUp(fourthPlayer, fourthPlayerView, new Controller(gameHandler, fourthPlayer, fourthPlayerView));

        gameHandler.setSkull(5);
        gameHandler.setMap(2);
    }

    @Test
    public void getTurnPlayerID() {
        int ID = gameHandler.getTurnPlayerID();
        assertEquals(FIRST_ID,ID);
        gameHandler.incrementTurn();
        ID = gameHandler.getTurnPlayerID();
        assertEquals(SECOND_ID,ID);
    }

    @Test
    public void getPlayerByID() {
        Player playerObtained = gameHandler.getPlayerByID(FIRST_ID);
        assertEquals("TonyStark", playerObtained.getNickname());
        playerObtained = gameHandler.getPlayerByID(SECOND_ID);
        assertEquals("SteveRogers", playerObtained.getNickname());
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
            for (int i = 0; i < 3; i++) {
                p.receiveDamageBy(e2);
                e3.receiveDamageBy(e2);
            }
            for (int i = 0; i < 3; i++) {
                p.receiveDamageBy(e1);
                e3.receiveDamageBy(e1);
            }
            for (int i = 0; i < 3; i++) {
                p.receiveDamageBy(e3);
                e3.receiveDamageBy(p);
            }
            for (int i = 0; i < 6; i++) {
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

        //test player's skull != 1
        setUp();
        e1 = gameHandler.getPlayerByID(1);
        p = gameHandler.getPlayerByID(2);
        e2 = gameHandler.getPlayerByID(3);
        e3 = gameHandler.getPlayerByID(4);

        CellSpawn c = new CellSpawn(null, null,null, null, 1,1,null);
        ArrayList<Cell> a = new ArrayList<>();
        a.add(c);new Room(c, "blue", a);
        p.resurrection();
        kill(p, e1, e2, e3);
        gameHandler.checkDeath();
        assertEquals(0, p.getNumPoints());
        assertEquals(5, e2.getNumPoints());
        assertEquals(8, e1.getNumPoints());
        assertEquals(2, e3.getNumPoints());

        //test isFrenzyDeath
        setUp();
        e1 = gameHandler.getPlayerByID(1);
        p = gameHandler.getPlayerByID(2);
        e2 = gameHandler.getPlayerByID(3);
        e3 = gameHandler.getPlayerByID(4);

        p.setFrenzyDeath();
        kill(p, e1, e2, e3);
        assertEquals(0, p.getNumPoints());
        assertEquals(1, e2.getNumPoints());
        assertEquals(4, e1.getNumPoints());
        assertEquals(1, e3.getNumPoints());
        assertEquals(0, p.getDamage().size());

        //test skull=0
        setUp();
        gameHandler = new GameHandler(gameHandler.getOrderPlayerList(), 1);
        setZeroSkull();
        e1 = gameHandler.getPlayerByID(1);
        p = gameHandler.getPlayerByID(2);
        e2 = gameHandler.getPlayerByID(3);
        e3 = gameHandler.getPlayerByID(4);

        kill(p, e1, e2, e3);
        assertEquals(0, p.getNumPoints());
        assertEquals(7+1, e2.getNumPoints());
        assertEquals(10+2+2, e1.getNumPoints());
        assertEquals(4+1, e3.getNumPoints());
        assertEquals(0, p.getDamage().size());

        setUp();
        gameHandler = new GameHandler(gameHandler.getOrderPlayerList(), 1);
        setZeroSkull();
        e1 = gameHandler.getPlayerByID(1);
        p = gameHandler.getPlayerByID(2);
        e2 = gameHandler.getPlayerByID(3);
        e3 = gameHandler.getPlayerByID(4);
        //test skull=0
        try {
            for (int i = 0; i < 1; i++) p.receiveDamageBy(e2);
            for (int i = 0; i < 2; i++) p.receiveDamageBy(e3);
            for (int i = 0; i < 3; i++) p.receiveDamageBy(e1);
        } catch (YouDeadException | YouOverkilledException e) {
        } finally {
            for(Player p1 : gameHandler.getOrderPlayerList()) {
                gameHandler.cashPoint(p1, false, true);
            }
            assertEquals(0, p.getNumPoints());
            assertEquals(7+1, e2.getNumPoints());
            assertEquals(10+2, e1.getNumPoints());
            assertEquals(4+1, e3.getNumPoints());
        }
    }

    private void kill(Player dead, Player killer, Player e2, Player e3) throws NotPresentException {
        try {
            for (int i = 0; i < 3; i++) dead.receiveDamageBy(e2);
            for (int i = 0; i < 3; i++) dead.receiveDamageBy(killer);
            for (int i = 0; i < 3; i++) dead.receiveDamageBy(e3);
            for (int i = 0; i < 6; i++) dead.receiveDamageBy(killer);
        } catch (YouDeadException | YouOverkilledException e) {
            try {
                dead.receiveDamageBy(killer); //the kill shot
            } catch (YouDeadException | YouOverkilledException ex) {
                gameHandler.checkDeath();
            }
        }
    }

    private void setZeroSkull() throws NotPresentException{
        Player e1 = gameHandler.getPlayerByID(1);
        Player p = gameHandler.getPlayerByID(2);
        Player e2 = gameHandler.getPlayerByID(3);
        Player e3 = gameHandler.getPlayerByID(4);
        kill(p, e1, e2, e3);
    }

    @Test
    public void cashSkullBoardPoint() throws NotPresentException{
        Player e1 = gameHandler.getPlayerByID(1);
        Player p = gameHandler.getPlayerByID(2);
        Player e2 = gameHandler.getPlayerByID(3);
        Player e3 = gameHandler.getPlayerByID(4);

        kill(p, e1, e2, e3);
        kill(e2, e1, e2, e3);
        gameHandler.incrementTurn();
        kill(e1, p, e2, e3);

        int pointP = p.getNumPoints();
        int pointE1 = e1.getNumPoints();
        int pointE2 = e2.getNumPoints();
        int pointE3 = e3.getNumPoints();

        gameHandler.cashSkullBoardPoint();
        assertEquals(pointP + 6, p.getNumPoints());
        assertEquals(pointE2, e2.getNumPoints());
        assertEquals(pointE1+8, e1.getNumPoints());
        assertEquals(pointE3, e3.getNumPoints());

        //test draw
        setUp();
        e1 = gameHandler.getPlayerByID(1);
        p = gameHandler.getPlayerByID(2);
        e2 = gameHandler.getPlayerByID(3);
        e3 = gameHandler.getPlayerByID(4);

        kill(p, e1, e2, e3);
        gameHandler.incrementTurn();
        kill(e1, p, e2, e3);

        pointP = p.getNumPoints();
        pointE1 = e1.getNumPoints();
        pointE2 = e2.getNumPoints();
        pointE3 = e3.getNumPoints();

        gameHandler.cashSkullBoardPoint();
        assertEquals(pointP + 6, p.getNumPoints());
        assertEquals(pointE2, e2.getNumPoints());
        assertEquals(pointE1+8, e1.getNumPoints());
        assertEquals(pointE3, e3.getNumPoints());
    }

    @Test
    public void getRanking() throws NotPresentException {
        Player e1 = gameHandler.getPlayerByID(1);
        Player p = gameHandler.getPlayerByID(2);
        Player e2 = gameHandler.getPlayerByID(3);
        Player e3 = gameHandler.getPlayerByID(4);

        e1.addPoints(20);
        p.addPoints(21);
        e2.addPoints(30);
        e3.addPoints(21);

        assertEquals(e2, gameHandler.getRanking().get(0));
        assertEquals(p, gameHandler.getRanking().get(1));
        assertEquals(e1, gameHandler.getRanking().get(3));
        gameHandler.incrementTurn();
        gameHandler.incrementTurn();
        gameHandler.incrementTurn();

        kill(e1, e3, e2, p);
        p.addPoints(4);  //p and e3 have the same points

        assertEquals(e2, gameHandler.getRanking().get(0));
        assertEquals(e1, gameHandler.getRanking().get(3));
        assertEquals(e3, gameHandler.getRanking().get(1));
    }

    @Test
    public void testPossibleCharacter() {
        Player firstPlayer = new Player("TonyStark", FIRST_ID);
        Player secondPlayer = new Player("SteveRogers", SECOND_ID);
        Player thirdPlayer = new Player("PeterParker", THIRD_ID);
        Player fourthPlayer = new Player("CarolDenvers", 4);
        ArrayList<Player> players = new ArrayList<>();
        players.add(firstPlayer);
        players.add(secondPlayer);
        players.add(thirdPlayer);
        players.add(fourthPlayer);

        gameHandler = new GameHandler(players, 5);

        assertEquals(5, gameHandler.possibleCharacter().size());
        firstPlayer.setCharacter(new Character(3));
        assertEquals(4, gameHandler.possibleCharacter().size());
        secondPlayer.setCharacter(new Character(1));
        assertEquals(3, gameHandler.possibleCharacter().size());
        for(Character character : gameHandler.possibleCharacter()) {
            assertNotEquals(3, character.getId());
            assertNotEquals(1, character.getId());
        }
    }

    @Test
    public void isDisconnected() {
        firstPlayer.setConnected(false);
        assertTrue(gameHandler.isDisconnected(firstPlayer.getNickname()));
        assertFalse(gameHandler.isDisconnected(secondPlayer.getNickname()));
    }

    @Test (expected = IllegalArgumentException.class)
    public void isDisconnectedEx() {
        firstPlayer.setConnected(false);
        gameHandler.isDisconnected("Parker");
    }

    @Test
    public void nextTurnSimple() {
        assertEquals(firstPlayer.getID(), gameHandler.getTurnPlayerID());
        gameHandler.nextTurn();
        assertEquals(secondPlayer.getID(), gameHandler.getTurnPlayerID());
        gameHandler.nextTurn(); //third player
        gameHandler.nextTurn(); //fourth player
        gameHandler.nextTurn();
        assertEquals(firstPlayer.getID(), gameHandler.getTurnPlayerID());
    }

    @Test
    public void nextTurnDisconnect() {
        secondPlayer.setConnected(false);
        thirdPlayer.setConnected(false);
        assertEquals(firstPlayer.getID(), gameHandler.getTurnPlayerID());
        gameHandler.nextTurn();
        assertEquals(fourthPlayer.getID(), gameHandler.getTurnPlayerID());
    }

    @Test
    public void nextTurnDeath() throws NotPresentException {
        kill(thirdPlayer, firstPlayer, secondPlayer, fourthPlayer);
        assertEquals(firstPlayer.getID(), gameHandler.getTurnPlayerID());
        gameHandler.nextTurn();
        //the turn is not passed because not everyone has spawned yet
        assertEquals(firstPlayer.getID(), gameHandler.getTurnPlayerID());
    }

    @Test

    public void nextTurnRespawnNotConnectedPlayer() throws NotPresentException {
        //thirdPlayer is not in a cell spawn, he is disconnected and he is killed
        thirdPlayer.setPosition(gameHandler.getCellByCoordinate(0,2));
        assertFalse(thirdPlayer.getCell() instanceof CellSpawn);
        thirdPlayer.setConnected(false);
        kill(thirdPlayer, firstPlayer, secondPlayer, fourthPlayer);
        assertEquals(firstPlayer.getID(), gameHandler.getTurnPlayerID());
        gameHandler.nextTurn();
        //when the killer passes his turn, the disconnected killed player has automatically respawned
        assertTrue(thirdPlayer.getCell() instanceof CellSpawn);
        //the turn is passed, since everyone has respawned
        assertEquals(secondPlayer.getID(), gameHandler.getTurnPlayerID());
    }

}