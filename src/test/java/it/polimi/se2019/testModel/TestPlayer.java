package it.polimi.se2019.testModel;

import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlayer {
    private Player player;

    @Before
    public void initTest() {
        player = new Player("Elon Musk", new Character("Ditruttore", "lilla"), 1);
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
}
