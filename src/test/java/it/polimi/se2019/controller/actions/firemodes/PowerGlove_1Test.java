package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.FireMessage;
import it.polimi.se2019.view.ViewControllerMess.FireModeMessage;
import it.polimi.se2019.view.ViewControllerMess.PlayerMessage;
import it.polimi.se2019.view.ViewControllerMess.WeaponMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PowerGlove_1Test {
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private Player targetPlayer4;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
    private Cell commonCell;
    private PlayerView playerView;
    private StateController stateController;

    private final static int WEAPON_ID = 10;
    private final static int FIREMODE_ID = 1;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 0);
        targetPlayer1 = new Player("SteveRogers", new Character("CapAmerica", "blue"), 1);
        targetPlayer2 = new Player("Hulk", new Character("Hulk", "yellow"), 2);
        targetPlayer3 = new Player("Thor", new Character("GodOfThunder", "purple"), 3);
        targetPlayer4 = new Player("PeterParker", new Character("SpiderMan", "red"), 4);


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

        //author and target 1 in the same cell
        commonCell = gameHandler.getCellByCoordinate(2,1);
        authorPlayer.setPosition(commonCell);
        targetPlayer1.setPosition(commonCell);

        //target 2, 3 and 4 in another cell visible
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(2,0));
        targetPlayer3.setPosition(gameHandler.getCellByCoordinate(2,2));
        targetPlayer4.setPosition(gameHandler.getCellByCoordinate(3,1));

        authorPlayer.setAmmoBag(3,3,3);


        //weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);


        //add firemode
        FireModeMessage fireModeMessage = new FireModeMessage(FIREMODE_ID, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
    }

    @Test
    public void oneTarget() throws Exception {
        controller.update(null,
                new PlayerMessage(2, authorPlayer.getID(), playerView));

        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));

        assertEquals(0, targetPlayer1.getDamage().size());
        assertEquals(1, targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer4.getDamage().size());
        assertEquals(gameHandler.getCellByCoordinate(2,0), authorPlayer.getCell());
        assertEquals(2, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer4.getMark().getMarkReceived().size());
    }

    @Test
    public void twoTarget() throws Exception {
        controller.update(null,
                new PlayerMessage(2, authorPlayer.getID(), playerView));
        controller.update(null,
                new PlayerMessage(4, authorPlayer.getID(), playerView));

        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));

        assertEquals(0, targetPlayer1.getDamage().size());
        assertEquals(1, targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer4.getDamage().size());
        assertEquals(gameHandler.getCellByCoordinate(2,0), authorPlayer.getCell());
        assertEquals(2, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer4.getMark().getMarkReceived().size());
    }

    @Test
    public void withMark() throws Exception {
        targetPlayer2.receiveMarkBy(authorPlayer);
        targetPlayer2.receiveMarkBy(targetPlayer1);

        controller.update(null,
                new PlayerMessage(targetPlayer2.getID(), authorPlayer.getID(), playerView));

        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));

        assertEquals(0, targetPlayer1.getDamage().size());
        assertEquals(2, targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer4.getDamage().size());
        assertEquals(gameHandler.getCellByCoordinate(2,0), authorPlayer.getCell());
        assertEquals(3, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer4.getMark().getMarkReceived().size());
    }

    @After
    public void himself() {
        assertEquals(0, authorPlayer.getDamage().size());
        assertEquals(0, authorPlayer.getMark().getMarkReceived().size());
    }

}