package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestLockRifle_1 {
    private Player authorPlayer;
    private Player targetPlayer;
    GameHandler gameHandler;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 2008);
        targetPlayer = new Player("SteveRogers", new Character("CapAmerica", "blue"), 2011);

        ArrayList<Player> players = new ArrayList<>();
        players.add(authorPlayer);
        players.add(targetPlayer);
        gameHandler = new GameHandler(players);

        //TODO bisogna impostare tutto gamehandler prima

    }

    @Test
    public void sendPossibleTarget() {
    }

    @Test
    public void fire() {

    }

    @Test
    public void controlMessage() {

    }

    @Test
    public void getMessageListExpected() {
    }

    @Test
    public void giveOnlyMarks() {
    }
}