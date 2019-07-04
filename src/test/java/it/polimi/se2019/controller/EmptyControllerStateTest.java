package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class EmptyControllerStateTest {
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

        controller.setState(new EmptyControllerState(controller, gameHandler));
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
    //controls the all these methods write the correct string to playerView
    public void notPossiblePositive() {
        NopeMessage nopeMessage = new NopeMessage(authorPlayer.getID(), playerView);
        controller.update(null, nopeMessage);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleCell(1,1);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleFiremode(1);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleOptional(1);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handlePlayer(1);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleTagback(new TagbackGrenadeCard(ColorRYB.BLUE,1,1));
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleTargeting(new TargetingScopeCard(ColorRYB.BLUE,1,1), new AmmoBag(0,1,0));
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleWeaponCard(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        });
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleFire();
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        controller.getState().handleDiscardWeapon(1);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
    }

    @Test
    public void handleReloadPositive() {
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
        assertTrue(controller.getState() instanceof HasReloadedControllerState);
    }

    @Test
    public void handleReloadNotPresentNegative() {
        ReloadMessage reloadMessage = new ReloadMessage(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        }, authorPlayer.getID(), playerView);
        controller.update(null, reloadMessage);
        assertEquals(EmptyControllerState.NOT_PRESENT_WEAPON_RELOAD
                + EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
    }

    @Test
    public void handleAlreadyReloadedNegative() {
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
        assertEquals(EmptyControllerState.WEAPON_LOADED_RELOAD
                + EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
    }

    @Test
    public void handleNotEnoughAmmoNegative() {
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
        assertEquals(EmptyControllerState.NOT_ENOUGH_AMMO_RELOAD
                + EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
    }

    @Test
    public void handleDiscardWeaponPositive() {
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

            @Override
            public int getID() {
                return 1;
            }
        };

        try {
            authorPlayer.addWeaponCard(weaponCard);
        } catch (TooManyException e) {
            //should't happen
        }
        assertFalse(authorPlayer.getWeaponCardList().isEmpty());
        DiscardWeaponMessage discardWeaponMessage = new DiscardWeaponMessage(weaponCard,authorPlayer.getID(), playerView);
        controller.update(null, discardWeaponMessage);
        assertTrue(authorPlayer.getWeaponCardList().isEmpty());
    }

    @Test
    public void handleDiscardWeaponNegative() {
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

            @Override
            public int getID() {
                return 1;
            }
        };
        assertTrue(authorPlayer.getWeaponCardList().isEmpty());
        DiscardWeaponMessage discardWeaponMessage = new DiscardWeaponMessage(weaponCard,authorPlayer.getID(), playerView);
        controller.update(null, discardWeaponMessage);
        assertEquals(EmptyControllerState.WEAPON_NOT_PRESENT + EmptyControllerState.SELECT_ACTION_REQUEST,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleDiscardPowerupPositive() {
        PowerupCard powerupCard = gameHandler.getPowerupCardByID(1);

        try {
            authorPlayer.addPowerupCard(powerupCard);
        } catch (TooManyException e) {
            //should't happen
        }
        assertFalse(authorPlayer.getPowerupCardList().isEmpty());
        DiscardPowerupMessage discardPowerupMessage = new DiscardPowerupMessage(powerupCard,authorPlayer.getID(), playerView);
        controller.update(null, discardPowerupMessage);
        assertTrue(authorPlayer.getPowerupCardList().isEmpty());
    }

    @Test
    public void handleDiscardPowerupNegative() {
        PowerupCard powerupCard = gameHandler.getPowerupCardByID(1);
        assertTrue(authorPlayer.getPowerupCardList().isEmpty());
        DiscardPowerupMessage discardPowerupMessage = new DiscardPowerupMessage(powerupCard,authorPlayer.getID(), playerView);
        controller.update(null, discardPowerupMessage);
        assertEquals(EmptyControllerState.POWERUP_NOT_PRESENT_DISCARD + EmptyControllerState.SELECT_ACTION_REQUEST,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleNewtonPositive() {
        NewtonCard newtonCard = new NewtonCard(ColorRYB.BLUE,1,1);
        try {
            authorPlayer.addPowerupCard(newtonCard);
        } catch (TooManyException e) {
            //shouldn't happen
        }
        NewtonMessage newtonMessage = new NewtonMessage(newtonCard, authorID, playerView);
        controller.update(null, newtonMessage);
        assertTrue(controller.getState() instanceof NewtonSelectedControllerState);
    }

    @Test
    public void handleNewtonNegative() {
        NewtonCard newtonCard = new NewtonCard(ColorRYB.BLUE,1,1);
        //we don't add the powerup to the player
        NewtonMessage newtonMessage = new NewtonMessage(newtonCard, authorID, playerView);
        controller.update(null, newtonMessage);
        assertEquals(EmptyControllerState.POWERUP_NOT_PRESENT_USE + EmptyControllerState.SELECT_ACTION_REQUEST,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handleTeleporterPositive() {
        TeleporterCard teleporterCard = new TeleporterCard(ColorRYB.BLUE,1,1);
        try {
            authorPlayer.addPowerupCard(teleporterCard);
        } catch (TooManyException e) {
            //shouldn't happen
        }
        TeleporterMessage teleporterMessage = new TeleporterMessage(teleporterCard, authorID, playerView);
        controller.update(null, teleporterMessage);
        assertTrue(controller.getState() instanceof TeleporterSelectedControllerState);
    }

    @Test
    public void handleTeleporterNegative() {
        TeleporterCard teleporterCard = new TeleporterCard(ColorRYB.BLUE,1,1);;
        //we don't add the powerup to the player
        TeleporterMessage teleporterMessage = new TeleporterMessage(teleporterCard, authorID, playerView);
        controller.update(null, teleporterMessage);
        assertEquals(EmptyControllerState.POWERUP_NOT_PRESENT_USE + EmptyControllerState.SELECT_ACTION_REQUEST,
                playerView.getLastStringPrinted());
    }

    @Test
    public void handlePassTurn() {
        PassTurnMessage passTurnMessage = new PassTurnMessage(authorID, playerView);
        controller.update(null, passTurnMessage);
        assertTrue(controller.getState() instanceof NotYourTurnState);
    }

    /**
     * we test the disconnection message
     */
    @Test
    public void handleDisconnection() {
        ReconnectionMessage reconnectionMessage = new ReconnectionMessage(false,authorID, playerView);
        controller.update(null, reconnectionMessage);
        assertTrue(controller.getState() instanceof DisconnectedControllerState);
    }


}