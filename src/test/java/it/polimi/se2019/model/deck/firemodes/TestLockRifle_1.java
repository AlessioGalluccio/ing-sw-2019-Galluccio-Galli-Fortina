package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.*;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.PlayerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestLockRifle_1 {
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
    private FireMode lockRifle_1;
    private Cell commonCell;
    private Cell notVisibleCell;
    private PlayerView playerView;

    private final static int LOCK_RIFLE_WEAPON_ID = 14;
    private final static int LOCK_RIFLE_FIREMODE_ID = 141;



    @Before
    public void setUp() throws Exception {
        //TODO playerview non testata
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
        controller = new Controller(gameHandler, null);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);
        shoot = new Shoot(gameHandler,controller);
        controller.setState(new ActionSelectedControllerState(controller,gameHandler, shoot));


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


        //Lock_Rifle weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(LOCK_RIFLE_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        shoot.addWeapon(weapon);

        //add firemode
        lockRifle_1 = gameHandler.getFireModeByID(LOCK_RIFLE_FIREMODE_ID);
        shoot.addFireMode(lockRifle_1.getID());


    }

    @Test
    public void sendPossibleTarget() {
        //TODO trovare un metodo per testarlo
        //lockRifle_1.sendPossibleTargetsAtStart();

    }

    @Test
    public void firePositiveOneTargetNoOptional() throws Exception {

        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.fire();
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(1, targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(1, authorPlayer.getMark().getMarkDone().size());

    }

    @Test (expected = WrongInputException.class)
    public void fireNegativeNotVisible() throws Exception{
        //we add a not visible target, and this makes it throw and exception
        shoot.addPlayerTarget(targetPlayer3.getID());
    }

    @Test
    public void firePositiveWithOptional() throws Exception {

        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.addOptional(1);
        shoot.addPlayerTarget(targetPlayer2.getID());
        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(1, targetPlayer1.getMark().getMarkReceived().size());

        //target 2
        assertEquals(0,targetPlayer2.getDamage().size());
        assertEquals(1, targetPlayer2.getMark().getMarkReceived().size());

        //author
        assertEquals(2, authorPlayer.getMark().getMarkDone().size());

        //cost after optional
        assertEquals(2, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(3, authorPlayer.getAmmo().getBlueAmmo());

    }

    @Test
    public void firePositiveNoTargetForOptional()  throws Exception{
        //even if optional has no target, you can fire
        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.addOptional(1);
        shoot.fire();

        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(1, targetPlayer1.getMark().getMarkReceived().size());

        assertEquals(0, targetPlayer2.getDamage().size());
    }


    @Test
    public void firePositiveWithTargeting() throws Exception {
        int targetingID = 1;
        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.BLUE,targetingID, targetingID);
        authorPlayer.addPowerupCard(card);

        AmmoBag ammoCostTargeting = new AmmoBag(0,0,1);

        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.addTargetingScope(targetingID,ammoCostTargeting);
        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(3,targetPlayer1.getDamage().size());
        assertEquals(1, targetPlayer1.getMark().getMarkReceived().size());

        //author
        assertEquals(1, authorPlayer.getMark().getMarkDone().size());

        //cost after targetin with blue ammo
        assertEquals(3, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(2, authorPlayer.getAmmo().getBlueAmmo());


    }

    @Test
    public void firePositiveWithTargetingAndOptional() throws Exception {
        int targetingID = 1;
        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.BLUE,targetingID, targetingID);
        authorPlayer.addPowerupCard(card);

        AmmoBag ammoCostTargeting = new AmmoBag(0,0,1);

        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.addTargetingScope(targetingID,ammoCostTargeting);
        shoot.addOptional(1);
        shoot.addPlayerTarget(targetPlayer2.getID());
        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(3,targetPlayer1.getDamage().size());
        assertEquals(1, targetPlayer1.getMark().getMarkReceived().size());

        //target 2
        assertEquals(0,targetPlayer2.getDamage().size());
        assertEquals(1, targetPlayer2.getMark().getMarkReceived().size());

        //author
        assertEquals(2, authorPlayer.getMark().getMarkDone().size());

        //cost after targetin with blue ammo and optional with red ammo
        assertEquals(2, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(2, authorPlayer.getAmmo().getBlueAmmo());


    }

    @Test(expected = WrongInputException.class)
    public void fireNegativeNoTargets()  throws Exception{
        shoot.fire();
    }

    @Test
    public void selectedYourself() throws Exception{
        PlayerMessage message = new PlayerMessage(authorPlayer.getID(), authorPlayer.getID(), playerView);
        controller.update(null, message);
        //shoot.addPlayerTarget(authorPlayer.getID());
        assertEquals("Error,you have selected yourself" + LockRifle_1.FIRST_MSG_STR, playerView.getLastStringPrinted());
    }




    @Test
    public void controlMessage() {

    }

    @Test
    public void getMessageListExpected() {
    }

    @Test
    public void giveOnlyMarks() {
    }
}