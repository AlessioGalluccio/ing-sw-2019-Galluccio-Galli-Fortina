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

public class HasReloadedControllerStateTest {
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
        controller = new Controller(gameHandler, null, playerView);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);

        controller.setState(new HasReloadedControllerState(controller, gameHandler));
        stateController = controller.getState();
    }



    @Test
    //can't choose an action in this state
    public void handleAction() {
        assertEquals(HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        ActionMessage actionMessage = new ActionMessage(5,authorPlayer.getID(), playerView);
        controller.update(null,actionMessage);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }


    @Test
    //can't select a cell in this state
    public void handleCell() {
        assertEquals(HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        CellMessage message = new CellMessage(1,1,authorPlayer.getID(), playerView);
        controller.update(null,message);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }

    @Test
    //controls that all these methods write the correct string to playerView
    public void ReloadOrPassPositive() {
        NopeMessage nopeMessage = new NopeMessage(authorPlayer.getID(), playerView);
        controller.update(null, nopeMessage);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleAction(1);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleCell(1,1);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleFiremode(1);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleNewton(new NewtonCard(ColorRYB.BLUE,1,1));
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleOptional(1);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handlePlayer(1);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleReload(1);
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleTagback(new TagbackGrenadeCard(ColorRYB.BLUE,1,1));
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleTargeting(new TargetingScopeCard(ColorRYB.BLUE,1,1), new AmmoBag(0,1,0));
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleTeleporter(new TeleporterCard(ColorRYB.BLUE,1,1));
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleWeaponCard(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        });
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
        controller.getState().handleFire();
        assertEquals(HasReloadedControllerState.CANT_DO_ALREADY_RELOADED
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }

    @Test
    //reloading if valid input
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
        assertEquals(HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }

    @Test
    //weapon not present
    public void handleReloadNotPresentNegative() {
        ReloadMessage reloadMessage = new ReloadMessage(new WeaponCard() {
            @Override
            public List<FireMode> getFireMode() {
                return null;
            }
        }, authorPlayer.getID(), playerView);
        controller.update(null, reloadMessage);
        assertEquals(HasReloadedControllerState.NOT_PRESENT_WEAPON_RELOAD
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }

    @Test
    //weapon already loaded
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
        assertEquals(HasReloadedControllerState.WEAPON_LOADED_RELOAD
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }

    @Test
    //not enough ammo for reloading
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
        assertEquals(HasReloadedControllerState.NOT_ENOUGH_AMMO_RELOAD
                + HasReloadedControllerState.RELOAD_OR_PASS, playerView.getLastStringPrinted());
    }


    @Test
    //can discard the weapon
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
    //weapon not present
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
        assertEquals(HasReloadedControllerState.WEAPON_NOT_PRESENT + HasReloadedControllerState.RELOAD_OR_PASS,
                playerView.getLastStringPrinted());
    }

    @Test
    //can discard powerup
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
    //powerup not present
    public void handleDiscardPowerupNegative() {
        PowerupCard powerupCard = gameHandler.getPowerupCardByID(1);
        assertTrue(authorPlayer.getPowerupCardList().isEmpty());
        DiscardPowerupMessage discardPowerupMessage = new DiscardPowerupMessage(powerupCard,authorPlayer.getID(), playerView);
        controller.update(null, discardPowerupMessage);
        assertEquals(HasReloadedControllerState.POWERUP_NOT_PRESENT_DISCARD + HasReloadedControllerState.RELOAD_OR_PASS,
                playerView.getLastStringPrinted());
    }


}