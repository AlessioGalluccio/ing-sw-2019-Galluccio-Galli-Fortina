package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.NopeMessage;
import it.polimi.se2019.view.ViewControllerMess.PlayerMessage;
import it.polimi.se2019.view.ViewControllerMess.TagbackGrenadeMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class NotYourTurnStateTest {

    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private Cell commonCell;
    private Cell notVisibleCell;
    private StateController stateController;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new it.polimi.se2019.model.player.Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new it.polimi.se2019.model.player.Character("CapAmerica", "blue"), 2011);
        targetPlayer2 = new Player("Hulk", new it.polimi.se2019.model.player.Character("Hulk", "yellow"), 3);
        targetPlayer3 = new Player("Thor", new it.polimi.se2019.model.player.Character("GodOfThunder", "purple"), 4);

        //we add the players to the game
        ArrayList<Player> players = new ArrayList<>();

        players.add(targetPlayer1);

        players.add(authorPlayer); //authorPlayer is added as second, in this way it's not his turn

        players.add(targetPlayer2);
        players.add(targetPlayer3);

        //settings of mock connection
        Server serverMock = mock(Server.class);
        Player playerCopyMock = mock(Player.class);
        playerView = new PlayerView(serverMock, playerCopyMock);
        gameHandler = new GameHandler(players, 8);
        gameHandler.setMap(1);
        controller = new Controller(gameHandler, null, playerView);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);

        controller.setState(new NotYourTurnState(controller, gameHandler));
        stateController = controller.getState();

        //author and target 1 in the same cell
        commonCell = gameHandler.getCellByCoordinate(1,1);
        authorPlayer.setPosition(commonCell);
        targetPlayer1.setPosition(commonCell);

        //target 2 in another room
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(0,1));
    }

    /**
     * here it's tested the response when there's an invalid input
     */
    @Test
    public void waitYourTurnResponse() {
        NopeMessage nopeMessage = new NopeMessage(authorPlayer.getID(), playerView);
        controller.update(null, nopeMessage);
        controller.getState().handleAction(1);
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleCell(1,1);
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleFiremode(1);
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleNewton(new NewtonCard(ColorRYB.BLUE,1,1));
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleOptional(1);
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handlePlayer(1);
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleReload(1);
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleTargeting(new TargetingScopeCard(ColorRYB.BLUE,1,1), new AmmoBag(0,1,0));
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleTeleporter(new TeleporterCard(ColorRYB.BLUE,1,1));
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleWeaponCard(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        });
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleFire();
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleDiscardWeapon(1);
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
        controller.getState().handleDiscardPowerup(1);
        assertEquals(NotYourTurnState.NOT_YOUR_TURN_RESPONSE, playerView.getLastStringPrinted());
    }

    @Test
    public void handleTagbackPositive() {
        try{
            authorPlayer.receiveDamageBy(targetPlayer1);
        }catch (Exception e){
            //shouldn't happen
        }
        PowerupCard tagbackGrenadeCard = gameHandler.getPowerupCardByID(3); //ID of a tagback
        try{
            authorPlayer.addPowerupCard(tagbackGrenadeCard);
        }catch (TooManyException e){
            //shouldn't happen
        }
        assertTrue(authorPlayer.getPowerupCardList().size() == 1);
        TagbackGrenadeMessage message =
                new TagbackGrenadeMessage((TagbackGrenadeCard) tagbackGrenadeCard,authorPlayer.getID(), playerView);
        controller.update(null, message);

        //System.out.println(playerView.getLastStringPrinted());

        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null, playerMessage);

        //System.out.println(playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().isEmpty());
        assertEquals(1,targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(authorPlayer, targetPlayer1.getMark().getMarkReceived().get(0));

    }

    @Test
    public void handleReconnectionDisconnected(){
        stateController.handleReconnection(false);
        assertEquals( true, controller.getState() instanceof DisconnectedControllerState);
    }

    @Test
    public void handleReconnectionNoEffect(){
        stateController.handleReconnection(true);
        assertEquals(false, controller.getState() instanceof DisconnectedControllerState );
        assertEquals(true, controller.getState() instanceof NotYourTurnState);
    }
}