package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CyberBlade_1Test {
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
    private FireMode firemode;
    private Cell commonCell;
    private Cell notVisibleCell;
    private PlayerView playerView;
    private StateController stateController;

    private final static int CYBERBLADE_WEAPON_ID = 21;
    private final static int CYBERBLADE_FIREMODE_ID = 211;

    private Controller controller2;
    private PlayerView playerView2;



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

        //author, target 1 and target 2 in the same cell
        commonCell = gameHandler.getCellByCoordinate(1,1);
        authorPlayer.setPosition(commonCell);
        targetPlayer1.setPosition(commonCell);
        targetPlayer2.setPosition(commonCell);

        //target 3 is in another room
        //TODO controlla che sia un'altra statnza!!
        notVisibleCell = gameHandler.getCellByCoordinate(1,2);
        targetPlayer3.setPosition(notVisibleCell);

        authorPlayer.setAmmoBag(3,3,3);


        //weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(CYBERBLADE_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);

        //System.out.println(playerView.getLastStringPrinted());

        //add firemode
        FireModeMessage fireModeMessage = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
        //System.out.println(playerView.getLastStringPrinted());

    }

    @Test
    public void firePositiveOneTargetNoOptional() throws Exception {
        System.out.println(playerView.getLastStringPrinted());
        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage);
        System.out.println(playerView.getLastStringPrinted());
        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);
        System.out.println(playerView.getLastStringPrinted());
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(0, authorPlayer.getMark().getMarkDone().size());

    }

    @Test
    public void firePositiveOneTargetFirstOptional() throws Exception {
        //System.out.println(playerView.getLastStringPrinted());

        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage);
        //System.out.println(playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(1,authorPlayer.getID(), playerView);
        controller.update(null,optionalMessage);
        //System.out.println(playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(0,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        //System.out.println(playerView.getLastStringPrinted());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);
        //System.out.println(playerView.getLastStringPrinted());

        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, authorPlayer.getCell().getCoordinateX());
        assertEquals(1, authorPlayer.getCell().getCoordinateY());

        assertEquals(3, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(3, authorPlayer.getAmmo().getBlueAmmo());


    }
    @Test
    public void firePositiveOneTargetFirstAndSecondOptional() throws Exception {
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(0,1));
        //System.out.println(playerView.getLastStringPrinted());

        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage);
        //System.out.println(playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(1,authorPlayer.getID(), playerView);
        controller.update(null,optionalMessage);
        //System.out.println(playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(0,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        //System.out.println(playerView.getLastStringPrinted());

        OptionalMessage optionalMessage2 = new OptionalMessage(2,authorPlayer.getID(), playerView);
        controller.update(null,optionalMessage2);
        //System.out.println(playerView.getLastStringPrinted());

        PlayerMessage playerMessage2 = new PlayerMessage(targetPlayer2.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage2);
        //System.out.println(playerView.getLastStringPrinted());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);
        //System.out.println(playerView.getLastStringPrinted());

        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(2,targetPlayer2.getDamage().size());
        assertEquals(0, authorPlayer.getCell().getCoordinateX());
        assertEquals(1, authorPlayer.getCell().getCoordinateY());

        assertEquals(3, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(2, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(3, authorPlayer.getAmmo().getBlueAmmo());


    }

    @Test
    public void firePositiveOneTargetFirstAndSecondOptionalWithTargeting() throws Exception {
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(0,1));
        System.out.println(playerView.getLastStringPrinted());

        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage);
        System.out.println(playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(1,authorPlayer.getID(), playerView);
        controller.update(null,optionalMessage);
        System.out.println(playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(0,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        System.out.println(playerView.getLastStringPrinted());

        OptionalMessage optionalMessage2 = new OptionalMessage(2,authorPlayer.getID(), playerView);
        controller.update(null,optionalMessage2);
        System.out.println(playerView.getLastStringPrinted());

        PlayerMessage playerMessage2 = new PlayerMessage(targetPlayer2.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage2);
        System.out.println(playerView.getLastStringPrinted());

        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.BLUE, 1,1);
        authorPlayer.addPowerupCard(card);
        TargetingScopeMessage targetingScopeMessage = new TargetingScopeMessage(card, ColorRYB.RED, authorPlayer.getID(),playerView);
        controller.update(null, targetingScopeMessage);
        System.out.println(playerView.getLastStringPrinted());

        controller.update(null,playerMessage);
        System.out.println(playerView.getLastStringPrinted());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);
        System.out.println(playerView.getLastStringPrinted());

        assertEquals(3,targetPlayer1.getDamage().size());
        assertEquals(2,targetPlayer2.getDamage().size());
        assertEquals(0, authorPlayer.getCell().getCoordinateX());
        assertEquals(1, authorPlayer.getCell().getCoordinateY());

        assertEquals(2, authorPlayer.getAmmo().getRedAmmo()); //paid targeting scope
        assertEquals(2, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(3, authorPlayer.getAmmo().getBlueAmmo());


    }
}