package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.DiscardPowerupMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MustRespawnControllerStateTest {
    private int authorID = 1;
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private StateController stateController;
    private static String MUST_RESPAWN = "Please, do a respawn";

    @Before
    public void setUp() throws Exception {

        //settings of mock connection
        Server serverMock = mock(Server.class);

        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 1);
        targetPlayer1 = new Player("SteveRogers", new Character("CapAmerica", "blue"), 2);
        targetPlayer2 = new Player("PeterParker", new Character("SpiderMan", "red"), 3);
        targetPlayer3 = new Player("CarolDenvers", new Character("CapMarvel", "white"), 4);

        gameHandler = new GameHandler(99);

        playerView = new PlayerView(serverMock, authorPlayer);
        PlayerView secondPlayerView = new PlayerView(serverMock, targetPlayer1);
        PlayerView thirdPlayerView = new PlayerView(serverMock, targetPlayer2);
        PlayerView fourthPlayerView = new PlayerView(serverMock, targetPlayer3);

        controller = new Controller(gameHandler, authorPlayer, playerView);
        Controller secondController = new Controller(gameHandler, targetPlayer1, secondPlayerView);
        Controller thirdController = new Controller(gameHandler, targetPlayer2, thirdPlayerView);
        Controller fourthController = new Controller(gameHandler, targetPlayer3, fourthPlayerView);

        controller.setState(new MustRespawnControllerState(controller, gameHandler));
        stateController = controller.getState();

        gameHandler.setUp(authorPlayer, playerView, controller);
        gameHandler.setUp(targetPlayer1, secondPlayerView, secondController);
        gameHandler.setUp(targetPlayer2, thirdPlayerView, thirdController);
        gameHandler.setUp(targetPlayer3, fourthPlayerView, fourthController);

        gameHandler.setSkull(0);
        gameHandler.setMap(2);
        gameHandler.setSuddenDeath(false);

    }

    @Test
    public void handleCardSpawn() {
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST,playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().size() == 1);
        PowerupCard card = authorPlayer.getPowerupCardList().get(0);

        DiscardPowerupMessage discardPowerupMessage = new DiscardPowerupMessage(card,authorPlayer.getID(), playerView);
        controller.update(null, discardPowerupMessage);
        assertTrue(controller.getState() instanceof NotYourTurnState);

    }
}