package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.PointCard;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestPlayer {
    private Player player;
    private Player enemy;
    private GameHandler gameHandler;
    private int MAX_AMMO = 3;

    @Before
    public void initTest() {
        player = new Player("Nikola Tesla", new Character("CoilMan", "yellow"), 1856);
        enemy = new Player("Thomas Edison", new Character("LightBulbMan", "white"), 1931);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(enemy);
        gameHandler = new GameHandler(players);

    }

    @Test
    public void testAddPowerup(){
        try {
            player.addPowerupCard(new TeleporterCard(ColorRYB.RED));
        } catch (TooManyException e) {
            e.printStackTrace();
        }

        assertEquals(1, player.getPowerupCardList().size());
    }

    @Test(expected = TooManyException.class)
    public void testAddPowerupException() throws TooManyException {
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED));
        player.addPowerupCard(new TeleporterCard(ColorRYB.RED));
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
            player.receiveMark(enemy);
        } catch (TooManyException e) {
            e.printStackTrace();
        }
        assertEquals(enemy.getID(), player.getMark().getMarkReceived().get(0).getID());
        assertEquals(player.getID(), enemy.getMark().getMarkDone().get(0).getID());
    }

}
