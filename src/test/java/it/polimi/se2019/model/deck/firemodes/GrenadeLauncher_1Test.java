package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.NotYourTurnState;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class GrenadeLauncher_1Test {
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

    private final static int GRENADE_WEAPON_ID = 19;
    private final static int GRENADE_FIREMODE_ID = 191;

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

        //Another controller
        Server serverMock2 = mock(Server.class);
        Player playerCopyMock2 = mock(Player.class);
        playerView2 = new PlayerView(serverMock2, playerCopyMock2);
        controller2 = new Controller(gameHandler, null, playerView2);
        controller2.setPlayerView(playerView2);
        controller2.setAuthor(targetPlayer1);

        controller2.setState(new NotYourTurnState(controller2, gameHandler, false));


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
        WeaponCard weapon = gameHandler.getWeaponCardByID(GRENADE_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);

        //System.out.println(playerView.getLastStringPrinted());

        //add firemode
        firemode = gameHandler.getFireModeByID(GRENADE_FIREMODE_ID);
        FireModeMessage fireModeMessage = new FireModeMessage(firemode.getID(), authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
        //System.out.println(playerView.getLastStringPrinted());

    }

    @Test
    public void firePositiveOneTargetNoOptionalAndNoMovementTest() throws Exception {

        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage);
        shoot.fire();
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(1,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(0, authorPlayer.getMark().getMarkDone().size());

    }

    @Test
    public void firePositiveOneTargetNoOptionalButWithMovementTest() throws Exception {
        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage);
        shoot.addCell(0,1);
        shoot.fire();
        assertEquals(1,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getCell().getCoordinateX());
        assertEquals(1,targetPlayer1.getCell().getCoordinateY());

    }

    @Test
    public void firePositiveOneTargetWithOptionalAndMovementTest() throws Exception {
        PlayerMessage playerMessage = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage);

        //System.out.println(playerView.getLastStringPrinted());

        /* test for arrival of another message from another player
        PlayerMessage playerMessage2 = new PlayerMessage(targetPlayer1.getID(), targetPlayer1.getID(), playerView2);
        controller2.update(null, playerMessage2);
        System.out.println("Player 2: " + playerView2.getLastStringPrinted());
        */

        CellMessage cellMessage = new CellMessage(0,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);

        //System.out.println(playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);

        //System.out.println(playerView.getLastStringPrinted());

        CellMessage cellMessage2 = new CellMessage(0,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage2);

        //System.out.println(playerView.getLastStringPrinted());

        shoot.fire();
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getCell().getCoordinateX());
        assertEquals(1,targetPlayer1.getCell().getCoordinateY());

    }

}