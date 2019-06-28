package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class NewtonSelectedControllerStateTest {
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private NewtonCard newtonCard = null;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new it.polimi.se2019.model.player.Character("CapAmerica", "blue"), 2011);
        targetPlayer2 = new Player("Hulk", new it.polimi.se2019.model.player.Character("Hulk", "yellow"), 3);

        //we add the players to the game
        ArrayList<Player> players = new ArrayList<>();
        players.add(authorPlayer);
        players.add(targetPlayer1);


        //settings of mock connection
        Server serverMock = mock(Server.class);
        Player playerCopyMock = mock(Player.class);
        playerView = new PlayerView(serverMock, playerCopyMock);
        gameHandler = new GameHandler(players, 8);
        gameHandler.setMap(1);

        while(newtonCard == null){
            PowerupCard card = gameHandler.getPowerupDeck().pick();
            if(card instanceof NewtonCard){
                newtonCard = (NewtonCard) card;

            }
        }

        controller = new Controller(gameHandler, null, playerView);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);

        //add card and ammo
        authorPlayer.addPowerupCard(newtonCard);
        authorPlayer.setAmmoBag(0,0,0);

        controller.setState(new NewtonSelectedControllerState(controller, gameHandler, newtonCard));

        Cell startingCell = gameHandler.getCellByCoordinate(1,1);
        authorPlayer.setPosition(startingCell);
        targetPlayer2.setPosition(startingCell);
    }

    @Test
    public void handleAction() {
        ActionMessage message = new ActionMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handlePositive() {
        try{
            targetPlayer1.setPosition(gameHandler.getCellByCoordinate(0,2));
        }catch (NotPresentException e){
            //shouldn't happen
        }
        assertEquals(NewtonSelectedControllerState.SELECT_TARGET_NEWTON, playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(newtonCard));

        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null, playerMessage);
        assertEquals(NewtonSelectedControllerState.SELECT_CELL_NEWTON, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,2,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);

        assertEquals(2,targetPlayer1.getCell().getCoordinateX());
        assertEquals(2,targetPlayer1.getCell().getCoordinateY());
        assertTrue(controller.getState() instanceof EmptyControllerState);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST,
                playerView.getLastStringPrinted());
        assertTrue(!authorPlayer.getPowerupCardList().contains(newtonCard));
    }


    @Test
    public void handleCellNotPresent() {
        try{
            targetPlayer1.setPosition(gameHandler.getCellByCoordinate(0,2));
        }catch (NotPresentException e){
            //shouldn't happen
        }
        assertEquals(NewtonSelectedControllerState.SELECT_TARGET_NEWTON, playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(newtonCard));
        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null, playerMessage);
        assertEquals(NewtonSelectedControllerState.SELECT_CELL_NEWTON, playerView.getLastStringPrinted());
        CellMessage cellMessage = new CellMessage(1,3,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(0,targetPlayer1.getCell().getCoordinateX());
        assertEquals(2,targetPlayer1.getCell().getCoordinateY());
        assertTrue(controller.getState() instanceof NewtonSelectedControllerState);
        assertEquals(NewtonSelectedControllerState.CELL_NOT_PRESENT + NewtonSelectedControllerState.SELECT_CELL_NEWTON,
                playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(newtonCard));
    }

    @Test
    public void handleCellNotValid() {
        try{
            targetPlayer1.setPosition(gameHandler.getCellByCoordinate(0,2));
        }catch (NotPresentException e){
            //shouldn't happen
        }
        assertEquals(NewtonSelectedControllerState.SELECT_TARGET_NEWTON, playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(newtonCard));
        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null, playerMessage);
        assertEquals(NewtonSelectedControllerState.SELECT_CELL_NEWTON, playerView.getLastStringPrinted());
        CellMessage cellMessage = new CellMessage(1,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(0,targetPlayer1.getCell().getCoordinateX());
        assertEquals(2,targetPlayer1.getCell().getCoordinateY());
        assertTrue(controller.getState() instanceof NewtonSelectedControllerState);
        assertEquals(NewtonSelectedControllerState.NOT_VALID_CELL + NewtonSelectedControllerState.SELECT_CELL_NEWTON,
                playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(newtonCard));
    }

    @Test
    public void handleTooManyTargets() {
        try{
            targetPlayer1.setPosition(gameHandler.getCellByCoordinate(0,2));
        }catch (NotPresentException e){
            //shouldn't happen
        }
        assertEquals(NewtonSelectedControllerState.SELECT_TARGET_NEWTON, playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(newtonCard));
        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null, playerMessage);
        assertEquals(NewtonSelectedControllerState.SELECT_CELL_NEWTON, playerView.getLastStringPrinted());
        PlayerMessage playerMessage2 = new PlayerMessage(targetPlayer2.getID(), authorPlayer.getID(), playerView);
        controller.update(null, playerMessage2);

        assertEquals(0,targetPlayer1.getCell().getCoordinateX());
        assertEquals(2,targetPlayer1.getCell().getCoordinateY());
        assertTrue(controller.getState() instanceof NewtonSelectedControllerState);
        assertEquals(NewtonSelectedControllerState.PLAYER_ALREADY_SELECTED_NEWTON + NewtonSelectedControllerState.SELECT_CELL_NEWTON,
                playerView.getLastStringPrinted());
        assertTrue(authorPlayer.getPowerupCardList().contains(newtonCard));
    }


    @Test
    public void handleFiremode() {
        FireModeMessage message = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleNewton() {
        NewtonMessage message = new NewtonMessage(new NewtonCard(ColorRYB.BLUE,1,1), authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
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
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
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
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleTagback() {
        TagbackGrenadeMessage message = new TagbackGrenadeMessage(new TagbackGrenadeCard(ColorRYB.BLUE,1,1), authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleTargeting() {
        TargetingScopeMessage message = new TargetingScopeMessage(new TargetingScopeCard(ColorRYB.BLUE,1,1),ColorRYB.BLUE, authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleTeleporter() {
        TeleporterMessage message = new TeleporterMessage(new TeleporterCard(ColorRYB.BLUE,1,1), authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
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
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleFire() {
        FireMessage message = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleDiscardPowerup() {
        DiscardPowerupMessage message = new DiscardPowerupMessage(new NewtonCard(ColorRYB.BLUE,1,1),  authorPlayer.getID(), playerView);
        controller.update(null, message);
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
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
        assertEquals(StateController.CANT_DO_THIS + NewtonSelectedControllerState.SELECT_TARGET_NEWTON,
                playerView.getLastStringPrinted());
    }

}