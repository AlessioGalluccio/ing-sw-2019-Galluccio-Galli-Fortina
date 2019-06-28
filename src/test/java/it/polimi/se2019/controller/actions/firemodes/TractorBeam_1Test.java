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
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TractorBeam_1Test {

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

    private final static int TRACTORBEAM_WEAPON_ID = 2;
    private final static int TRACTORBEAM_2_FIREMODE_ID = 21;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new Character("CapAmerica", "blue"), 1);
        targetPlayer2 = new Player("Hulk", new Character("Hulk", "yellow"), 2);
        targetPlayer3 = new Player("Thor", new Character("GodOfThunder", "purple"), 3);

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

        //author and target 1 in the same cell
        commonCell = gameHandler.getCellByCoordinate(1,1);
        authorPlayer.setPosition(commonCell);
        targetPlayer1.setPosition(commonCell);

        //target 2 in another room
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(2,1));

        //target 3 distant not targettable
        notVisibleCell = gameHandler.getCellByCoordinate(3,2);
        targetPlayer3.setPosition(notVisibleCell);

        authorPlayer.setAmmoBag(3,3,3);


        //weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(TRACTORBEAM_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);


        //add firemode
        FireModeMessage fireModeMessage = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
    }

    @Test
    public void oneTarget() throws Exception {
        controller.update(null,
                new PlayerMessage(1, authorPlayer.getID(), playerView));
        controller.update(null,
                new CellMessage(0,1, authorPlayer.getID(), playerView));
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));

        assertEquals(gameHandler.getCellByCoordinate(0,1), targetPlayer1.getCell());
        assertEquals(1, targetPlayer1.getDamage().size());
        assertEquals(0, authorPlayer.getDamage().size());
        assertEquals(0, authorPlayer.getMark().getMarkReceived().size());

        setUp();
        controller.update(null,
                new PlayerMessage(2, authorPlayer.getID(), playerView));
        controller.update(null,
                new CellMessage(1,0, authorPlayer.getID(), playerView));
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));

        assertEquals(gameHandler.getCellByCoordinate(1,0), targetPlayer2.getCell());
        assertEquals(1, targetPlayer2.getDamage().size());

        setUp();
        controller.update(null,
                new PlayerMessage(3, authorPlayer.getID(), playerView));
        assertEquals("The target you chose is too distant from you.\n" +
                "Select a player, even if you can't see it. ", playerView.getLastStringPrinted());
        controller.update(null,
                new CellMessage(1,0, authorPlayer.getID(), playerView));
        assertEquals("You can't do this now. Select a player, even if you can't see it. ", playerView.getLastStringPrinted());
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));

        assertEquals(gameHandler.getCellByCoordinate(3,2), targetPlayer3.getCell());
        assertEquals(0, targetPlayer3.getDamage().size());
    }

    @Test
    public void withMark() throws Exception  {
        targetPlayer1.receiveDamageBy(authorPlayer);
        targetPlayer1.receiveDamageBy(authorPlayer);


        controller.update(null,
                new PlayerMessage(1, authorPlayer.getID(), playerView));
        controller.update(null,
                new CellMessage(0,1, authorPlayer.getID(), playerView));
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));

        assertEquals(gameHandler.getCellByCoordinate(0,1), targetPlayer1.getCell());
        assertEquals(3, targetPlayer1.getDamage().size());
        assertEquals(0, authorPlayer.getDamage().size());

        setUp();
        targetPlayer2.receiveDamageBy(authorPlayer);
        controller.update(null,
                new PlayerMessage(2, authorPlayer.getID(), playerView));
        controller.update(null,
                new CellMessage(1,0, authorPlayer.getID(), playerView));
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));

        assertEquals(gameHandler.getCellByCoordinate(1,0), targetPlayer2.getCell());
        assertEquals(2, targetPlayer2.getDamage().size());
        assertEquals(0, authorPlayer.getDamage().size());
        assertEquals(0, authorPlayer.getMark().getMarkReceived().size());
    }


}