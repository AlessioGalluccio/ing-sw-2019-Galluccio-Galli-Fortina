package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.ShootFrenzyGroup1;
import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ActionSelectedControllerStateTest {

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
        authorPlayer = new Player("TonyStark", new it.polimi.se2019.model.player.Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new it.polimi.se2019.model.player.Character("CapAmerica", "blue"), 2011);
        targetPlayer2 = new Player("Hulk", new it.polimi.se2019.model.player.Character("Hulk", "yellow"), 3);
        targetPlayer3 = new Player("Thor", new it.polimi.se2019.model.player.Character("GodOfThunder", "purple"), 4);

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
        controller = new Controller(gameHandler, null, playerView);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);
        shoot = new Shoot(gameHandler, controller);

        controller.setState(new ActionSelectedControllerState(controller, gameHandler, shoot));
        stateController = controller.getState();
    }

    @Test
    //controller calls the right method
    public void correctCallOfHandleFromController(){

        TeleporterCard teleporterCard = new TeleporterCard(ColorRYB.BLUE,1,1);


        TeleporterMessage teleporterMessage = new TeleporterMessage(teleporterCard, 1, playerView);

        controller.update(null, teleporterMessage);

        assertEquals(StateController.CANT_DO_THIS + controller.getCopyMessageListExpected().get(0).getString(),
                playerView.getLastStringPrinted());

    }

    @Test
    //skip an action brings to EmptyControllerState
    public void skipPositive(){

        NopeMessage nopeMessage = new NopeMessage(authorPlayer.getID(), playerView);
        controller.update(null, nopeMessage);
        assertTrue(controller.getState() instanceof EmptyControllerState);
        assertEquals(1,controller.getNumOfActionTaken());

        ActionMessage actionMessage = new ActionMessage(Identificator.GRAB,authorPlayer.getID(),playerView);
        controller.update(null, actionMessage);
        assertTrue(controller.getState() instanceof ActionSelectedControllerState);
        controller.update(null, nopeMessage);
        assertTrue(controller.getState() instanceof EmptyControllerState);
        assertEquals(2,controller.getNumOfActionTaken());

        controller.update(null, actionMessage);
        //System.out.println(playerView.getLastStringPrinted());
        assertTrue(controller.getState() instanceof EmptyControllerState); //I have alredy done two actions

    }


    @Test
    //valid reloading with shootFrenzy
    public void handleReloadPositive() {
        //action ShootFrenzy permits reloading
        Action action = new ShootFrenzyGroup1(gameHandler,controller);
        controller.setState(new ActionSelectedControllerState(controller,gameHandler,action));
        try {
            authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,1));
        } catch (NotPresentException e) {
            //shouldn't happen
        }
        CellMessage cellMessage = new CellMessage(1,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);

        WeaponCard weaponCard = new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }

            @Override
            public List<ColorRYB> getReloadCost() {
                List<ColorRYB> list = new ArrayList<>();
                list.add(ColorRYB.RED);
                return list;
            }
        };
        weaponCard.unload();
        try {
            authorPlayer.setAmmoBag(1,0,0); //not enough ammo
        } catch (TooManyException e) {
            //shouldn0t happen
        }
        try {
            authorPlayer.addWeaponCard(weaponCard);
        } catch (TooManyException e) {
            //should't happen
        }
        ReloadMessage reloadMessage = new ReloadMessage(weaponCard, authorPlayer.getID(), playerView);
        controller.update(null, reloadMessage);
        assertTrue(weaponCard.isReloaded());
    }

    @Test
    //if weapon is not present
    public void handleReloadNotPresentNegative() {
        //action ShootFrenzy permits reloading
        Action action = new ShootFrenzyGroup1(gameHandler,controller);
        controller.setState(new ActionSelectedControllerState(controller,gameHandler,action));
        try {
            authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,1));
        } catch (NotPresentException e) {
            //shouldn't happen
        }
        CellMessage cellMessage = new CellMessage(1,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);

        ReloadMessage reloadMessage = new ReloadMessage(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        }, authorPlayer.getID(), playerView);
        controller.update(null, reloadMessage);
        assertEquals(ActionSelectedControllerState.WEAPON_NOT_PRESENT
                + ShootFrenzyGroup1.FIRST_MESSAGE, playerView.getLastStringPrinted());
    }

    @Test
    //if weapon is already reloaded
    public void handleAlreadyReloadedNegative() {
        //action ShootFrenzy permits reloading
        Action action = new ShootFrenzyGroup1(gameHandler,controller);
        controller.setState(new ActionSelectedControllerState(controller,gameHandler,action));
        try {
            authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,1));
        } catch (NotPresentException e) {
            //shouldn't happen
        }
        CellMessage cellMessage = new CellMessage(1,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);

        WeaponCard weaponCard = new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        };
        weaponCard.reload();
        try {
            authorPlayer.addWeaponCard(weaponCard);
        } catch (TooManyException e) {
            //should't happen
        }
        ReloadMessage reloadMessage = new ReloadMessage(weaponCard, authorPlayer.getID(), playerView);
        controller.update(null, reloadMessage);
        assertEquals(ActionSelectedControllerState.WEAPON_LOADED_RELOAD
                + ShootFrenzyGroup1.FIRST_MESSAGE, playerView.getLastStringPrinted());
    }

    @Test
    //if not enough ammo for reloading
    public void handleNotEnoughAmmoNegative() {
        //action ShootFrenzy permits reloading
        Action action = new ShootFrenzyGroup1(gameHandler,controller);
        controller.setState(new ActionSelectedControllerState(controller,gameHandler,action));
        try {
            authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,1));
        } catch (NotPresentException e) {
            //shouldn't happen
        }
        CellMessage cellMessage = new CellMessage(1,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);

        WeaponCard weaponCard = new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }

            @Override
            public List<ColorRYB> getReloadCost() {
                List<ColorRYB> list = new ArrayList<>();
                list.add(ColorRYB.RED);
                return list;
            }
        };
        weaponCard.unload();
        try {
            authorPlayer.setAmmoBag(0,0,0); //not enough ammo
        } catch (TooManyException e) {
            //shouldn0t happen
        }
        try {
            authorPlayer.addWeaponCard(weaponCard);
        } catch (TooManyException e) {
            //should't happen
        }
        ReloadMessage reloadMessage = new ReloadMessage(weaponCard, authorPlayer.getID(), playerView);
        controller.update(null, reloadMessage);
        assertEquals(ActionSelectedControllerState.NOT_ENOUGH
                + ShootFrenzyGroup1.FIRST_MESSAGE, playerView.getLastStringPrinted());
    }
}