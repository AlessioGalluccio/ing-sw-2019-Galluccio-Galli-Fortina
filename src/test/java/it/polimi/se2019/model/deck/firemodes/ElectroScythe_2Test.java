package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ElectroScythe_2Test {
    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
    private Cell commonCell;

    private final static int ELECTROSCYTHE_WEAPON_ID = 1;
    private final static int ELECTROSCYTHE_FIREMODE_2_ID = 12;

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
        PlayerView playerView = mock(PlayerView.class);
        gameHandler = new GameHandler(players, 8);
        gameHandler.setMap(1);
        controller = new Controller(gameHandler, null);
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

        //ElectroScythe weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(ELECTROSCYTHE_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        shoot.addWeapon(weapon);

        //add firemode
        FireMode electroScythe_1 = gameHandler.getFireModeByID(ELECTROSCYTHE_FIREMODE_2_ID);
        shoot.addFireMode(electroScythe_1.getID());
    }

    @Test
    public void firePositive() throws Exception {

        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

        //target 2
        assertEquals(2,targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());

        //target 3
        assertEquals(2,targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());

        //author
        assertEquals(0, authorPlayer.getMark().getMarkDone().size());

        //cost after optional
        assertEquals(2, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(2, authorPlayer.getAmmo().getBlueAmmo());
    }

    @Test
    public void firePositiveWithTargeting() throws Exception {
        int targetingID = 1;
        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.BLUE,targetingID, targetingID);
        authorPlayer.addPowerupCard(card);

        AmmoBag ammoCostTargeting = new AmmoBag(0,0,1);


        shoot.addTargetingScope(targetingID,ammoCostTargeting);
        shoot.addPlayerTarget(targetPlayer1.getID());

        shoot.fire();

        //target 1
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(3,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

        //cost after optional and targeting
        assertEquals(2, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(1, authorPlayer.getAmmo().getBlueAmmo());

    }

    @Test(expected = WrongInputException.class)
    public void fireNegativeWithTargeting() throws Exception {
        int targetingID = 1;
        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.BLUE,targetingID, targetingID);
        authorPlayer.addPowerupCard(card);

        AmmoBag ammoCostTargeting = new AmmoBag(0,0,1);

        Cell outsideCellWithNoTargets = gameHandler.getCellByCoordinate(3,2);
        authorPlayer.setPosition(outsideCellWithNoTargets);
        shoot.addTargetingScope(targetingID,ammoCostTargeting);
        shoot.addPlayerTarget(targetPlayer1.getID());

    }

}