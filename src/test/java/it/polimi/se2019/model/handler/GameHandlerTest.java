package it.polimi.se2019.model.handler;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameHandlerTest {

    private GameHandler gameHandler;
    private int FIRST_ID = 1;
    private int SECOND_ID = 2;

    @Before
    public void setUp() throws Exception {
        Player firstPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), FIRST_ID);
        Player secondPlayer = new Player("SteveRogers", new Character("CapAmerica", "blue"), SECOND_ID);
        ArrayList<Player> players = new ArrayList<>();
        players.add(firstPlayer);
        players.add(secondPlayer);
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
    public void getPlayerByID() {
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
    }
}