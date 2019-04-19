package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.PointCard;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestPlayer {
    private Player player;
    private int MAX_AMMO = 3;

    @Before
    public void initTest() {
        player = new Player("Elon Musk", new Character("FalconHeavy", "White"), 1971);
    }

    @Test
    public void testAddPowerup(){
        try {
            player.addPowerupCard(new TeleporterCard(ColorRYB.RED));
        } catch (TooManyCardException e) {
            e.printStackTrace();
        }

        assertEquals(1, player.getPowerupCardList().size());
    }

    @Test(expected = TooManyCardException.class)
    public void testAddPowerupException() throws TooManyCardException{
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

    @Test(expected = TooManyAmmoException.class)
    public void testSetAmmoException() throws TooManyAmmoException {
        player.setAmmoBag(4,2,1);
    }

    @Test
    public void testSetAmmo() {
        try {
            player.setAmmoBag(4,5,8);
        } catch (TooManyAmmoException e) {
            e.printStackTrace();
        }
        finally {
            assertEquals(MAX_AMMO, player.getAmmo().getBlueAmmo());
            assertEquals(MAX_AMMO, player.getAmmo().getRedAmmo());
            assertEquals(MAX_AMMO, player.getAmmo().getYellowAmmo());
        }
    }
}
