package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

public class NormalTest {
    private Modality modality;
    private GameHandler gameHandler;

    private Player firstPlayer;
    private Player secondPlayer;
    private Player thirdPlayer;
    private Player fourthPlayer;

    private Controller firstController;
    private Controller secondController;
    private Controller thirdController;
    private Controller fourthController;

    @Before
    public void setUp() {
        firstPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 1);
        secondPlayer = new Player("SteveRogers", new Character("CapAmerica", "blue"), 2);
        thirdPlayer = new Player("PeterParker", new Character("SpiderMan", "red"), 3);
        fourthPlayer = new Player("CarolDenvers", new Character("CapMarvel", "white"), 4);

        gameHandler = new GameHandler(99);

        Server serverMock = mock(Server.class);

        PlayerView firstPlayerView = new PlayerView(serverMock, firstPlayer);
        PlayerView secondPlayerView = new PlayerView(serverMock, secondPlayer);
        PlayerView thirdPlayerView = new PlayerView(serverMock, thirdPlayer);
        PlayerView fourthPlayerView = new PlayerView(serverMock, fourthPlayer);

        firstController = new Controller(gameHandler, firstPlayer, firstPlayerView);
        secondController = new Controller(gameHandler, secondPlayer, secondPlayerView);
        thirdController = new Controller(gameHandler, thirdPlayer, thirdPlayerView);
        fourthController = new Controller(gameHandler, fourthPlayer, fourthPlayerView);

        gameHandler.setUp(firstPlayer, firstPlayerView, firstController);
        gameHandler.setUp(secondPlayer, secondPlayerView, secondController);
        gameHandler.setUp(thirdPlayer, thirdPlayerView, thirdController);
        gameHandler.setUp(fourthPlayer, fourthPlayerView, fourthController);

        gameHandler.setSkull(5);
        gameHandler.setMap(2);

        modality = new Normal();
    }

    @Test
    public void getActionNormal() throws Exception {
        firstPlayer.receiveDamageBy(secondPlayer);
        firstPlayer.receiveDamageBy(secondPlayer);

        assertTrue(modality.getActionByID(Identificator.MOVE, firstController, gameHandler) instanceof Move);
        assertTrue(modality.getActionByID(Identificator.GRAB, firstController, gameHandler) instanceof Grab);
        assertTrue(modality.getActionByID(Identificator.SHOOT, firstController, gameHandler) instanceof Shoot);
    }

    @Test
    public void getActionAdrenaline() throws Exception {
        firstPlayer.receiveDamageBy(secondPlayer);
        firstPlayer.receiveDamageBy(secondPlayer);
        firstPlayer.receiveDamageBy(secondPlayer);

        assertTrue(modality.getActionByID(Identificator.MOVE, firstController, gameHandler) instanceof Move);
        assertTrue(modality.getActionByID(Identificator.GRAB, firstController, gameHandler) instanceof GrabAdrenaline);
        assertTrue(modality.getActionByID(Identificator.SHOOT, firstController, gameHandler) instanceof Shoot);

        firstPlayer.receiveDamageBy(secondPlayer);
        firstPlayer.receiveDamageBy(secondPlayer);
        firstPlayer.receiveDamageBy(secondPlayer);

        assertTrue(modality.getActionByID(Identificator.MOVE, firstController, gameHandler) instanceof Move);
        assertTrue(modality.getActionByID(Identificator.GRAB, firstController, gameHandler) instanceof GrabAdrenaline);
        assertTrue(modality.getActionByID(Identificator.SHOOT, firstController, gameHandler) instanceof ShootAdrenaline);
    }

    @Test (expected = WrongInputException.class)
    public void getActionException() throws Exception {
        firstPlayer.receiveDamageBy(secondPlayer);
        firstPlayer.receiveDamageBy(secondPlayer);
        firstPlayer.receiveDamageBy(secondPlayer);

        modality.getActionByID(0, firstController, gameHandler);
    }
}