package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.deck.TagbackGrenadeCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.PlayerMessage;
import it.polimi.se2019.view.ViewControllerMess.TagbackGrenadeMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

    @Test
    public void handleAction() {
    }

    @Test
    public void handleCardSpawn() {
    }

    @Test
    public void handleCell() {
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
    public void handlePlayer() {
    }

    @Test
    public void handleReload() {
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
        TagbackGrenadeMessage message =
                new TagbackGrenadeMessage((TagbackGrenadeCard) tagbackGrenadeCard,authorPlayer.getID(), playerView);
        controller.update(null, message);

        //System.out.println(playerView.getLastStringPrinted());

        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null, playerMessage);

        //System.out.println(playerView.getLastStringPrinted());

        assertEquals(1,targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(authorPlayer, targetPlayer1.getMark().getMarkReceived().get(0));

    }

    @Test
    public void handleTargeting() {
    }

    @Test
    public void handleTeleporter() {

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

    @Test
    public void handleIsTurnPlayerNow() {
        //ViewControllerMessage msg = new CellMessage(1,2);
        //stateController.handle(msg);


    }
}