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

public class ShotGun_2Test {
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

    private final static int SHOTGUN_WEAPON_ID = 9;


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
        targetPlayer1.setPosition(gameHandler.getCellByCoordinate(0,1)); //distant one from author
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(1,1)); //same cell of author
        targetPlayer3.setPosition(gameHandler.getCellByCoordinate(2,0)); //too distant
        targetPlayer4.setPosition(gameHandler.getCellByCoordinate(3,1));


        authorPlayer.setAmmoBag(3,3,3);


        //weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(SHOTGUN_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);

        //System.out.println(playerView.getLastStringPrinted());

        //add firemode
        FireModeMessage fireModeMessage = new FireModeMessage(2, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
        //System.out.println(playerView.getLastStringPrinted());

    }

    @Test
    public void firePositiveNoTargeting() throws Exception {
        assertEquals(ShotGun_2.SELECT_PLAYER_DISTANT_ONE, playerView.getLastStringPrinted());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);

        assertEquals(0,authorPlayer.getDamage().size());
        assertEquals(0,authorPlayer.getMark().getMarkReceived().size());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

    }

    @Test
    public void firePositiveWithTargeting() throws Exception {
        assertEquals(ShotGun_2.SELECT_PLAYER_DISTANT_ONE, playerView.getLastStringPrinted());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        TargetingScopeCard targetingScopeCard = new TargetingScopeCard(ColorRYB.BLUE,1,1);
        authorPlayer.addPowerupCard(targetingScopeCard);

        TargetingScopeMessage targetingScopeMessage = new TargetingScopeMessage(targetingScopeCard, ColorRYB.BLUE, authorPlayer.getID(), playerView);
        controller.update(null, targetingScopeMessage);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);

        assertEquals(0,authorPlayer.getDamage().size());
        assertEquals(0,authorPlayer.getMark().getMarkReceived().size());
        assertEquals(3,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

    }


    @Test
    public void fireNegativeSameCellTarget() throws Exception {
        assertEquals(ShotGun_2.SELECT_PLAYER_DISTANT_ONE, playerView.getLastStringPrinted());

        PlayerMessage playerMessage2 = new PlayerMessage(targetPlayer2.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage2);
        assertEquals(ShotGun_2.INVALID_TARGET_SHOTGUN2 + ShotGun_2.SELECT_PLAYER_DISTANT_ONE, playerView.getLastStringPrinted());

    }

    @Test
    public void fireNegativeTooDistantTarget() throws Exception {
        assertEquals(ShotGun_2.SELECT_PLAYER_DISTANT_ONE, playerView.getLastStringPrinted());

        PlayerMessage playerMessage3 = new PlayerMessage(targetPlayer3.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage3);
        assertEquals(ShotGun_2.INVALID_TARGET_SHOTGUN2 + ShotGun_2.SELECT_PLAYER_DISTANT_ONE, playerView.getLastStringPrinted());

    }

    @Test
    public void fireNegativeSelectedYourself() throws Exception {
        assertEquals(ShotGun_2.SELECT_PLAYER_DISTANT_ONE, playerView.getLastStringPrinted());

        PlayerMessage playerMessage = new PlayerMessage(authorPlayer.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage);
        assertEquals(ShotGun_2.INVALID_TARGET_SHOTGUN2 + ShotGun_2.SELECT_PLAYER_DISTANT_ONE, playerView.getLastStringPrinted());

    }

}