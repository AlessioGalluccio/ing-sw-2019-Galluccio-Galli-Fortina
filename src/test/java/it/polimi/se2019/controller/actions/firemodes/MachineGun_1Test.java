package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MachineGun_1Test {
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
    private Cell commonCell;

    private final static int MACHINE_WEAPON_ID = 15;

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
        PlayerView playerView = mock(PlayerView.class);
        gameHandler = new GameHandler(players, 8);
        gameHandler.setMap(1);
        controller = new Controller(gameHandler, null, playerView);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);
        shoot = new Shoot(gameHandler,controller);

        //author, target 1 and target 2 in the same cell
        commonCell = gameHandler.getCellByCoordinate(1,1);
        authorPlayer.setPosition(commonCell);
        targetPlayer1.setPosition(commonCell);
        targetPlayer2.setPosition(commonCell);
        targetPlayer3.setPosition(commonCell);

        authorPlayer.setAmmoBag(3,3,3);


        //Machine_Gun  weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(MACHINE_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        shoot.addWeapon(weapon);

        //add firemode
        shoot.addFireMode(1);


    }

    @Test
    public void firePositiveOneTargetNoOptional()  throws Exception {
        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.fire();
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(1,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(0, authorPlayer.getMark().getMarkDone().size());
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
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

        //target 2
        assertEquals(1,targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());

        //author
        assertEquals(0, authorPlayer.getMark().getMarkDone().size());

        //cost after optional
        assertEquals(3, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(2, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(3, authorPlayer.getAmmo().getBlueAmmo());

    }

    @Test
    public void firePositiveWithBothOptional() throws Exception {

        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.addPlayerTarget(targetPlayer2.getID());
        shoot.addOptional(1);
        shoot.addOptional(2);
        shoot.addPlayerTarget(targetPlayer3.getID());
        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

        //target 2
        assertEquals(2,targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());

        //target3
        assertEquals(1,targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());

        //author
        assertEquals(0, authorPlayer.getMark().getMarkDone().size());

        //cost after optional
        assertEquals(3, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(2, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(2, authorPlayer.getAmmo().getBlueAmmo());

    }

    @Test
    public void firePositiveWithTargetingAndBothOptional() throws Exception {
        int targetingID = 1;
        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.BLUE,targetingID, targetingID);
        authorPlayer.addPowerupCard(card);

        AmmoBag ammoCostTargeting = new AmmoBag(0,0,1);

        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.addTargetingScope(targetingID,ammoCostTargeting);
        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.addPlayerTarget(targetPlayer2.getID());
        shoot.addOptional(1);
        shoot.addOptional(2);
        shoot.addPlayerTarget(targetPlayer3.getID());
        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(3,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

        //target 2
        assertEquals(2,targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());

        //target3
        assertEquals(1,targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());

        //author
        assertEquals(0, authorPlayer.getMark().getMarkDone().size());

        //cost after optional
        assertEquals(3, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(2, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(1, authorPlayer.getAmmo().getBlueAmmo());
    }

    @After
    public void himself() {
        assertEquals(0, authorPlayer.getDamage().size());
        assertEquals(0, authorPlayer.getMark().getMarkReceived().size());
    }
}