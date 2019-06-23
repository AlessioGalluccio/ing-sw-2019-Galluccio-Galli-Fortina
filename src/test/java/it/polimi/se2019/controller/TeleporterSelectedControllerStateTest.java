package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;

public class TeleporterSelectedControllerStateTest {
    private Player authorPlayer;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private StateController stateController;
    private PowerupCard teleporterCard = null;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 2008);

        //we add the players to the game
        ArrayList<Player> players = new ArrayList<>();
        players.add(authorPlayer);

        //settings of mock connection
        Server serverMock = mock(Server.class);
        Player playerCopyMock = mock(Player.class);
        playerView = new PlayerView(serverMock, playerCopyMock);
        gameHandler = new GameHandler(players, 8);
        gameHandler.setMap(1);

        while(teleporterCard == null){
            PowerupCard card = gameHandler.getPowerupDeck().pick();
            if(card instanceof TeleporterCard){
                teleporterCard = card;

            }
        }

        controller = new Controller(gameHandler, null, playerView);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);

        //add card and ammo
        authorPlayer.addPowerupCard(teleporterCard);
        authorPlayer.setAmmoBag(0,0,0);

        controller.setState(new TeleporterSelectedControllerState(controller, gameHandler, (TeleporterCard)teleporterCard));
        stateController = controller.getState();

        Cell startingCell = gameHandler.getCellByCoordinate(1,1);
        authorPlayer.setPosition(startingCell);
    }

    @Test
    public void handleAction() {
        ActionMessage message = new ActionMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleCellPositive() {
        assertEquals(TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER, playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(teleporterCard));
        CellMessage cellMessage = new CellMessage(1,2,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(1,authorPlayer.getCell().getCoordinateX());
        assertEquals(2,authorPlayer.getCell().getCoordinateY());
        assertTrue(controller.getState() instanceof EmptyControllerState);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST,
                playerView.getLastStringPrinted());
        assertTrue(!authorPlayer.getPowerupCardList().contains(teleporterCard));
    }

    @Test
    public void handleCellNotPresentCell() {
        assertEquals(TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER, playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(teleporterCard));
        CellMessage cellMessage = new CellMessage(1,3,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(1,authorPlayer.getCell().getCoordinateX());
        assertEquals(1,authorPlayer.getCell().getCoordinateY());
        assertTrue(controller.getState() instanceof TeleporterSelectedControllerState);
        assertEquals(TeleporterSelectedControllerState.CELL_NOT_PRESENT + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(teleporterCard));
    }

    @Test
    public void handleFiremode() {
        FireModeMessage message = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleNewton() {
        NewtonMessage message = new NewtonMessage(new NewtonCard(ColorRYB.BLUE,1,1), authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleNope() {
        NopeMessage message = new NopeMessage(authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertTrue(controller.getState() instanceof EmptyControllerState);
    }

    @Test
    public void handleOptional() {
        OptionalMessage message = new OptionalMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handlePlayer() {
        PlayerMessage message = new PlayerMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleReload() {
        ReloadMessage message = new ReloadMessage(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        }, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleTagback() {
        TagbackGrenadeMessage message = new TagbackGrenadeMessage(new TagbackGrenadeCard(ColorRYB.BLUE,1,1), authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleTargeting() {
        TargetingScopeMessage message = new TargetingScopeMessage(new TargetingScopeCard(ColorRYB.BLUE,1,1),ColorRYB.BLUE, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleTeleporter() {
        TeleporterMessage message = new TeleporterMessage(new TeleporterCard(ColorRYB.BLUE,1,1), authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleWeaponCard() {
        WeaponMessage message = new WeaponMessage(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        }, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleFire() {
        FireMessage message = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleDiscardPowerup() {
        DiscardPowerupMessage message = new DiscardPowerupMessage(new PowerupCard(ColorRYB.BLUE,1,1) {
            @Override
            public ArrayList<Target> sendPossibleTarget(Player player, PlayerView playerView) {
                return null;
            }

            @Override
            public void useCard(Player author) {

            }
        }, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleDiscardWeapon() {
        DiscardWeaponMessage message = new DiscardWeaponMessage(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        }, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(TeleporterSelectedControllerState.CANT_DO_THIS + TeleporterSelectedControllerState.SELECT_CELL_TELEPORTER,
                playerView.getLastStringPrinted());
    }
}