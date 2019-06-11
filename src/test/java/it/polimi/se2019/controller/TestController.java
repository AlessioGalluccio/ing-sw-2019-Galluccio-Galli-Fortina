package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestController {
    private Controller controller;
    private int authorID = 1;
    private PlayerView playerView;


    @Before
    public void initTest() {
        Character character1 = new Character("Character1", "Yellow");
        Player temp = new Player("Bob", character1, 0);
        ArrayList<Player> players = new ArrayList<>();
        players.add(temp);
        GameHandler gameHandler = new GameHandler(players, 8);
        this.controller = new Controller(gameHandler, null);
        this.playerView = new PlayerView(temp);
    }

    /*

    @Test
    public void testMessageListReceivedEqual() {

        NopeMessage nopeMessage = new NopeMessage(authorID, playerView);
        controller.addMessageListReceived(nopeMessage);
        ArrayList<ViewControllerMessage> received = controller.getCopyMessageListReceived();
        assertEquals(nopeMessage.getMessageID(), received.get(0).getMessageID());
        TeleporterMessage teleporterMessage = new TeleporterMessage(new TeleporterCard(ColorRYB.RED, 1, 0), authorID, playerView);
        controller.addMessageListReceived(teleporterMessage);
        received = controller.getCopyMessageListReceived();
        assertEquals(teleporterMessage.getMessageID(), received.get(1).getMessageID());

    }

    @Test
    public void testMessageListReceivedNotEqual() {

        NopeMessage nopeMessage = new NopeMessage(authorID, playerView);
        controller.addMessageListReceived(new TeleporterMessage(new TeleporterCard(ColorRYB.RED, 2, 0), authorID, playerView));
        ArrayList<ViewControllerMessage> received = controller.getCopyMessageListReceived();
        assertNotEquals(nopeMessage.getMessageID(), received.get(0).getMessageID());
        
    }

    */


}
