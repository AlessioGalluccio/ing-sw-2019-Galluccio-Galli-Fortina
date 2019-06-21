package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.player.TooManyException;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.FireMessage;
import it.polimi.se2019.view.ViewControllerMess.FireModeMessage;
import it.polimi.se2019.view.ViewControllerMess.PlayerMessage;
import it.polimi.se2019.view.ViewControllerMess.WeaponMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import it.polimi.se2019.model.player.Character;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TractorBeam_2Test {

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
    private final static int TRACTORBEAM_2_FIREMODE_ID = 22;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new Character("CapAmerica", "blue"), 2011);
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
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(0,1));

        //target 3 distant 4 cells
        notVisibleCell = gameHandler.getCellByCoordinate(1,2);
        targetPlayer3.setPosition(notVisibleCell);

        authorPlayer.setAmmoBag(3,3,3);


        //weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(TRACTORBEAM_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);


        //add firemode
        FireModeMessage fireModeMessage = new FireModeMessage(2, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
    }

    @Test
    public void oneTargetSameCell() {
        controller.update(null,
                new PlayerMessage(2011, authorPlayer.getID(), playerView));
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));
        assertEquals(3, targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer3.getDamage().size());
    }

    @Test
    public void oneTargetDistant() {
        controller.update(null,
                new PlayerMessage(2, authorPlayer.getID(), playerView));
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));
        assertEquals(0, targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(3, targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer3.getDamage().size());
        assertEquals(authorPlayer.getCell(), targetPlayer2.getCell());
        assertEquals(2, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(2, authorPlayer.getAmmo().getRedAmmo());
    }

    @Test
    public void oneTargetTooDistant() {
        controller.update(null,
                new PlayerMessage(4, authorPlayer.getID(), playerView));
        assertEquals("The target you chose is too distant from you.\n" +
                "Select a player up to two cells distant from you, even if you can't see it. ",
                playerView.getLastStringPrinted());
        assertEquals(0, targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer3.getDamage().size());
    }

    @Test
    public void twoTarget() {
        controller.update(null,
                new PlayerMessage(2, authorPlayer.getID(), playerView));
        controller.update(null,
                new PlayerMessage(3, authorPlayer.getID(), playerView));
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));
        assertEquals(0, targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(3, targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer3.getDamage().size());
    }

    @Test
    public void withMark() throws TooManyException {
        targetPlayer2.receiveMarkBy(authorPlayer);
        controller.update(null,
                new PlayerMessage(2, authorPlayer.getID(), playerView));
        controller.update(null,
                new FireMessage(authorPlayer.getID(), playerView));
        assertEquals(0, targetPlayer1.getDamage().size());
        assertEquals(4, targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(0, targetPlayer3.getDamage().size());

    }

}