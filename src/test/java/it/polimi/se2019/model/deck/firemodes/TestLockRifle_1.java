package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.*;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
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
    GameHandler gameHandler;
    Controller controller;
    Shoot shoot;
    Cell commonCell;

    private final static int LOCK_RIFLE_WEAPON_ID = 14;


    @Before
    public void setUp() throws Exception {
        //TODO playerview non testata
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new Character("CapAmerica", "blue"), 2011);
        targetPlayer2 = new Player("Hulk", new Character("Hulk", "yellow"), 3);
        //targetPlayer3 = new Player("Thor", new Character("GodOfThunder", "purple"), 4);

        //we add the players to the game
        ArrayList<Player> players = new ArrayList<>();
        players.add(authorPlayer);
        players.add(targetPlayer1);
        players.add(targetPlayer2);
        //players.add(targetPlayer3);

        //settings of mock connection
        PlayerView playerView = mock(PlayerView.class);
        gameHandler = new GameHandler(players, 8);
        gameHandler.setMap(1);
        controller = new Controller(gameHandler);
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
        //targetPlayer3.setPosition(gameHandler.getCellByCoordinate(1,2));

        authorPlayer.setAmmoBag(3,3,3);


        //Lock_Rifle weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(LOCK_RIFLE_WEAPON_ID);

        weapon.reload();

        authorPlayer.addWeaponCard(weapon);

        //System.out.println(authorPlayer.getWeaponCardList().get(0).getID());
        //System.out.println(authorPlayer.getID());
        //System.out.println(shoot.getPlayerAuthor().getID());
        //System.out.println(shoot.getPlayerAuthor().getWeaponCardList().contains(weapon));
        //System.out.println(gameHandler.getOrderPlayerList().get(0));


        shoot.addWeapon(weapon);

        FireMode lockRifle_1 = gameHandler.getFireModeByID(141);
        shoot.addFireMode(lockRifle_1.getID());

        //System.out.println("La firemode ha null in costo? -> " + lockRifle_1.getCost().isEmpty());
        //System.out.println("costo dopo creazione ammo firemode -> " + AmmoBag.createAmmoFromList(lockRifle_1.getCost()));
        //System.out.println("costo dell'azione ora -> " + shoot.getCost());



        //TODO bisogna impostare tutto gamehandler prima

    }

    @Test
    public void sendPossibleTarget() {
    }

    @Test
    public void firePositiveOneTargetNoOptional() throws Exception {

        //System.out.println("chiamo gamehandler da firemode " + shoot.fireMode.getGameHandler());

        shoot.addPlayerTarget(targetPlayer1.getID());
        shoot.fire();
        assertEquals(authorPlayer.getID(),targetPlayer1.getDamage().get(0).getID());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(1, targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(1, authorPlayer.getMark().getMarkDone().size());

    }

    @Test
    public void firePositiveOneTargetWithOptional() throws Exception {

        //System.out.println("chiamo gamehandler da firemode " + shoot.fireMode.getGameHandler());

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