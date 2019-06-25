package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.FireModeMessage;
import it.polimi.se2019.view.ViewControllerMess.TargetingScopeMessage;
import it.polimi.se2019.view.ViewControllerMess.WeaponMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ElectroScythe_1Test {
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private Player targetPlayer4;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
    private FireMode firemode;
    private Cell commonCell;
    private Cell notVisibleCell;
    private PlayerView playerView;
    private StateController stateController;

    private final static int ELECTROSCYTHE_WEAPON_ID = 1;


    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new it.polimi.se2019.model.player.Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new it.polimi.se2019.model.player.Character("CapAmerica", "blue"), 2011);
        targetPlayer2 = new Player("Hulk", new it.polimi.se2019.model.player.Character("Hulk", "yellow"), 3);
        targetPlayer3 = new Player("Thor", new it.polimi.se2019.model.player.Character("GodOfThunder", "purple"), 4);
        targetPlayer4 = new Player("Widow", new it.polimi.se2019.model.player.Character("Spy", "black"), 5);

        //we add the players to the game
        ArrayList<Player> players = new ArrayList<>();
        players.add(authorPlayer);
        players.add(targetPlayer1);
        players.add(targetPlayer2);
        players.add(targetPlayer3);
        players.add(targetPlayer4);

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


        authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,1));
        targetPlayer1.setPosition(gameHandler.getCellByCoordinate(1,1)); //distant one from author
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(1,1)); //same cell of author
        targetPlayer3.setPosition(gameHandler.getCellByCoordinate(1,1)); //too distant
        targetPlayer4.setPosition(gameHandler.getCellByCoordinate(3,1));


        authorPlayer.setAmmoBag(3,3,3);


        //weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(ELECTROSCYTHE_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);


        //I don't add here the firemode because this weapon create the list of targets when firemode is created,
        // because it doesn't need more inputs by the player. So, add it at the start of every test

    }

    @Test
    public void firePositive() throws Exception {

        //add firemode
        //we must add here because  this weapon create the list of targets when firemode is created
        //read the setUp for further information
        FireModeMessage fireModeMessage = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(1,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

        //target 2
        assertEquals(1,targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());

        //target 3
        assertEquals(1,targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());

        //author
        assertEquals(0, authorPlayer.getDamage().size());

        //cost after optional
        assertEquals(3, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(3, authorPlayer.getAmmo().getBlueAmmo());
    }

    @Test
    public void firePositiveWithTargeting() throws Exception {
        //add firemode
        //we must add here because  this weapon create the list of targets when firemode is created
        //read the setUp for further information
        FireModeMessage fireModeMessage = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);

        int targetingID = 1;
        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.BLUE,targetingID, targetingID);
        authorPlayer.addPowerupCard(card);

        AmmoBag ammoCostTargeting = new AmmoBag(0,0,1);


        shoot.addTargetingScope(targetingID,ammoCostTargeting);
        shoot.addPlayerTarget(targetPlayer1.getID());

        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

        //cost after optional and targeting
        assertEquals(3, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(2, authorPlayer.getAmmo().getBlueAmmo());

    }
    
    public void fireNegativeWithTargeting() throws Exception {
        //we move the authorPlayer befaore adding the firemode, so the firemode wll create the empty target list

        Cell outsideCellWithNoTargets = gameHandler.getCellByCoordinate(3,2);
        AmmoBag ammoCostTargeting = new AmmoBag(0,0,1); //ammo for the targeting
        authorPlayer.setPosition(outsideCellWithNoTargets);

        //add firemode
        //we must add here because  this weapon create the list of targets when firemode is created
        //read the setUp for further information
        FireModeMessage fireModeMessage = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);

        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.RED,1,1);
        authorPlayer.addPowerupCard(card);
        TargetingScopeMessage targetingScopeMessage = new TargetingScopeMessage(card,ColorRYB.BLUE,authorPlayer.getID(), playerView);
        controller.update(null, targetingScopeMessage);
        assertEquals(FireMode.NO_TARGET_TARGETING + Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        assertEquals(0,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());
    }

    @After
    public void himself() {
        assertEquals(0, authorPlayer.getDamage().size());
        assertEquals(0, authorPlayer.getMark().getMarkReceived().size());
    }
}