package it.polimi.se2019.view.ViewControllerMess;

import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ViewControllerMessageTest {
    FireMessage fireMessage;
    PlayerView playerView;
    Player author;

    @Before
    public void setUp() throws Exception {
        author = new Player("Iron", new Character(1),1);
        playerView = mock(PlayerView.class);
        fireMessage = new FireMessage(author.getID(), playerView);
    }

    @Test
    public void authorIDTest() {
        assertEquals(author.getID(), fireMessage.getAuthorID());
    }

    @Test
    public void getMessageIDTest() {
        assertEquals(Identificator.FIRE_MESSAGE, fireMessage.getMessageID());
    }

    @Test
    public void getAuthorViewTest() {
        assertEquals(playerView, fireMessage.getAuthorView());
    }

    @Test
    public void setViewTest() {
        PlayerView playerView2 = mock(PlayerView.class);
        fireMessage.setView(playerView2);
        assertEquals(playerView2, fireMessage.getAuthorView());
    }
}