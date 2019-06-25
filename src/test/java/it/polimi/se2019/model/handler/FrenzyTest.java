package it.polimi.se2019.model.handler;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FrenzyTest {
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

        gameHandler.setSkull(0);
        gameHandler.setMap(2);
        gameHandler.setSuddenDeath(false);

        gameHandler.incrementTurn();
        gameHandler.nextTurn();

        modality = new Frenzy();
    }

    @Test
    public void getAction() throws Exception {
        assertTrue(modality.getActionByID(Identificator.GRAB, firstController, gameHandler) instanceof GrabFrenzy);
        assertTrue(modality.getActionByID(Identificator.SHOOT, firstController, gameHandler) instanceof ShootFrenzyGroup2);

        assertTrue(modality.getActionByID(Identificator.GRAB, secondController, gameHandler) instanceof GrabFrenzy);
        assertTrue(modality.getActionByID(Identificator.SHOOT, secondController, gameHandler) instanceof ShootFrenzyGroup2);

        assertTrue(modality.getActionByID(Identificator.MOVE, thirdController, gameHandler) instanceof MoveFrenzy);
        assertTrue(modality.getActionByID(Identificator.GRAB, thirdController, gameHandler) instanceof GrabAdrenaline);
        assertTrue(modality.getActionByID(Identificator.SHOOT, thirdController, gameHandler) instanceof ShootFrenzyGroup1);

        assertTrue(modality.getActionByID(Identificator.MOVE, fourthController, gameHandler) instanceof MoveFrenzy);
        assertTrue(modality.getActionByID(Identificator.GRAB, fourthController, gameHandler) instanceof GrabAdrenaline);
        assertTrue(modality.getActionByID(Identificator.SHOOT, fourthController, gameHandler) instanceof ShootFrenzyGroup1);
    }

    @Test (expected = WrongInputException.class)
    public void getActionException() throws WrongInputException {
        try {
            modality.getActionByID(Identificator.MOVE, secondController, gameHandler);
        } catch (WrongInputException e) {
            try {
                modality.getActionByID(0, firstController, gameHandler);
            } catch (WrongInputException ex) {
                modality.getActionByID(4, fourthController, gameHandler);
            }
        }
    }

}