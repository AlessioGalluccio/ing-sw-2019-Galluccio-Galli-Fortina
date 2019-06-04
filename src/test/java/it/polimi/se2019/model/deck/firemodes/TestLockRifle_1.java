package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.*;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestLockRifle_1 {
    private Player authorPlayer;
    private Player targetPlayer1;
    GameHandler gameHandler;
    Controller controller;
    Shoot shoot;
    Cell commonCell;
    FireMode lockRifle_1;


    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new Character("CapAmerica", "blue"), 2011);

        ArrayList<Player> players = new ArrayList<>();
        players.add(authorPlayer);
        players.add(targetPlayer1);

        PlayerView playerView = mock(PlayerView.class);
        gameHandler = new GameHandler(players, 8);
        controller = new Controller(gameHandler);
        controller.setPlayerView(playerView);
        controller.setAuthor(authorPlayer);
        shoot = new Shoot(gameHandler,controller);


        Border wall = new Wall();
        AmmoDeck deckMock = mock(AmmoDeck.class);
        commonCell = new CellAmmo(wall,wall,wall,wall,0,0, deckMock);
        CellSpawn cellSpawnMock = mock(CellSpawn.class);
        ArrayList cellList = new ArrayList();
        cellList.add(commonCell);
        Room room = new Room(cellSpawnMock,"RED", cellList);


        authorPlayer.setPosition(commonCell);
        targetPlayer1.setPosition(commonCell);

        authorPlayer.setAmmoBag(3,3,3);

        //WeaponDeck weaponDeck = new WeaponDeck();

        WeaponCard weapon = gameHandler.getWeaponCardByID(14);

        weapon.reload();

        authorPlayer.addWeaponCard(weapon);

        System.out.println(authorPlayer.getWeaponCardList().get(0).getID());
        System.out.println(authorPlayer.getID());
        System.out.println(shoot.getPlayerAuthor().getID());
        System.out.println(shoot.getPlayerAuthor().getWeaponCardList().contains(weapon));

        shoot.addWeapon(weapon);

        lockRifle_1 = gameHandler.getFireModeByID(141);
        shoot.addFireMode(lockRifle_1.getID());

        //TODO bisogna impostare tutto gamehandler prima

    }

    @Test
    public void sendPossibleTarget() {
    }

    @Test
    public void firePositive() throws Exception {
        lockRifle_1.addPlayerTarget(targetPlayer1.getID());
        lockRifle_1.fire();



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