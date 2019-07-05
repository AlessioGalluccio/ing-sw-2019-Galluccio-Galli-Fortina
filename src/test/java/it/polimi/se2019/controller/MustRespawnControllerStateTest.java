package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.map.CellSpawn;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.DiscardPowerupMessage;
import it.polimi.se2019.view.ViewControllerMess.NopeMessage;
import it.polimi.se2019.view.ViewControllerMess.PassTurnMessage;
import it.polimi.se2019.view.ViewControllerMess.ReconnectionMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    private Controller secondController;
    private  Controller thirdController;
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
        secondController = new Controller(gameHandler, targetPlayer1, secondPlayerView);
        thirdController = new Controller(gameHandler, targetPlayer2, thirdPlayerView);
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

        Cell commonCell = gameHandler.getCellByCoordinate(1,1);
        authorPlayer.setPosition(commonCell);
        targetPlayer1.setPosition(commonCell);
        targetPlayer2.setPosition(commonCell);
        targetPlayer3.setPosition(commonCell);

    }

    @Test
    //correct respawing
    public void handleCardSpawn() {
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST,playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().size() == 1);
        PowerupCard card = authorPlayer.getPowerupCardList().get(0);

        DiscardPowerupMessage discardPowerupMessage = new DiscardPowerupMessage(card,authorPlayer.getID(), playerView);
        controller.update(null, discardPowerupMessage);
        assertTrue(controller.getState() instanceof NotYourTurnState);
        assertEquals(targetPlayer1.getID(), gameHandler.getTurnPlayerID());
        assertEquals(1, authorPlayer.getSkull());
    }

    @Test
    //disconnection case
    public void handleDisconnection() {
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST,playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().size() == 1);

        ReconnectionMessage reconnectionMessage = new ReconnectionMessage(false,authorPlayer.getID(), playerView);
        controller.update(null, reconnectionMessage);
        assertTrue(authorPlayer.getCell() instanceof CellSpawn);
        assertTrue(controller.getState() instanceof DisconnectedControllerState);
        assertTrue(authorPlayer.getPowerupCardList().size() == 0);
    }

    @Test
    //controls that all these methods write the correct string to playerView
    public void youMustRepawnPositiveCall() {
        NopeMessage nopeMessage = new NopeMessage(authorPlayer.getID(), playerView);
        controller.update(null, nopeMessage);
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleAction(1);
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleCell(1,1);
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleFiremode(1);
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleNewton(new NewtonCard(ColorRYB.BLUE,1,1));
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleOptional(1);
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handlePlayer(1);
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleReload(1);
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleTagback(new TagbackGrenadeCard(ColorRYB.BLUE,1,1));
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleTargeting(new TargetingScopeCard(ColorRYB.BLUE,1,1), new AmmoBag(0,1,0));
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleTeleporter(new TeleporterCard(ColorRYB.BLUE,1,1));
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleWeaponCard(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        });
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handlePassTurn();
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleFire();
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleDiscardWeapon(1);
        assertEquals(MustRespawnControllerState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());
    }
}