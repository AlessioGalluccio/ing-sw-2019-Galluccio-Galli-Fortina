package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.ActionMessage;
import it.polimi.se2019.view.ViewControllerMess.CardSpawnChooseMessage;
import it.polimi.se2019.view.ViewControllerMess.CellMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class HasReloadedControllerStateTest {
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
    private FireMode lockRifle_1;
    private Cell commonCell;
    private Cell notVisibleCell;
    private StateController stateController;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new Character("CapAmerica", "blue"), 2011);
        targetPlayer2 = new Player("Hulk", new Character("Hulk", "yellow"), 3);
        targetPlayer3 = new Player("Thor", new Character("GodOfThunder", "purple"), 4);

        //we add the players to the game
        ArrayList<Player> players = new ArrayList<>();
        players.add(authorPlayer);
        players.add(targetPlayer1);
        players.add(targetPlayer2);
        players.add(targetPlayer3);

        //settings of mock connection
        Server serverMock = mock(Server.class);
        Player playerCopyMock = mock(Player.class);
        playerView = new PlayerView(serverMock, playerCopyMock);
        gameHandler = new GameHandler(players, 8);
        gameHandler.setMap(1);
        controller = new Controller(gameHandler, null);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);

        controller.setState(new HasReloadedControllerState(controller, gameHandler));
        stateController = controller.getState();
    }



    @Test
    public void handleAction() {
        ActionMessage actionMessage = new ActionMessage(5,authorPlayer.getID(), playerView);
        controller.update(null,actionMessage);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }

    @Test
    public void handleCardSpawn() {
        PowerupCard powerupCard1 = mock(PowerupCard.class);
        PowerupCard powerupCard2 = mock(PowerupCard.class);
        CardSpawnChooseMessage message = new CardSpawnChooseMessage(powerupCard1, powerupCard2,authorPlayer.getID(), playerView);
        controller.update(null,message);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }

    @Test
    public void handleCell() {
        CellMessage message = new CellMessage(1,1,authorPlayer.getID(), playerView);
        controller.update(null,message);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }

    @Test
    public void handleFiremode() {
    }

    @Test
    public void handleLogin() {
    }

    @Test
    public void handleNewton() {
    }

    @Test
    public void handleNope() {
    }

    @Test
    public void handleOptional() {
    }

    @Test
    public void handlePlayer() {
    }

    @Test
    public void handleReload() {
    }

    @Test
    public void handleTagback() {
    }

    @Test
    public void handleTargeting() {
    }

    @Test
    public void handleTeleporter() {
    }

    @Test
    public void handleWeaponCard() {
    }

    @Test
    public void handlePassTurn() {
    }

    @Test
    public void handleFire() {
    }

    @Test
    public void handleReconnection() {
    }
}