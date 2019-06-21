package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class HeatSeeker_1Test {

    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
    private FireMode heatSeeker_1;
    private Cell commonCell;
    private Cell notVisibleCell;

    private final static int HEAT_SEEKER_WEAPON_ID = 4;
    private final static int HEAT_SEEKER_FIREMODE_ID = 41;



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
        controller = new Controller(gameHandler, null, playerView);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);
        shoot = new Shoot(gameHandler,controller);

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


        //HeatSeeker weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(HEAT_SEEKER_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        shoot.addWeapon(weapon);

        //add firemode
        shoot.addFireMode(1);

    }

    @Test
    public void firePositive() throws Exception{
        //we add a not visible target, and this makes it throw and exception
        shoot.addPlayerTarget(targetPlayer3.getID());
        shoot.fire();
        assertEquals(3,targetPlayer3.getDamage().size());
    }

    @Test
    public void firePositiveWithOneOldMark() throws Exception{
        //we add a not visible target, and this makes it throw and exception
        targetPlayer3.receiveMarkBy(authorPlayer);
        shoot.addPlayerTarget(targetPlayer3.getID());
        shoot.fire();
        assertEquals(4,targetPlayer3.getDamage().size());
        assertEquals(0,targetPlayer3.getMark().getMarkReceived().size());
    }
}