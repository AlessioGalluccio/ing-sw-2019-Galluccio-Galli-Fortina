package it.polimi.se2019.model.deck.firemodes;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class VortexCannon_1Test {
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

    private final static int VORTEX_CANNON_WEAPON_ID = 18;


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


        authorPlayer.setPosition(gameHandler.getCellByCoordinate(3,0));
        targetPlayer1.setPosition(gameHandler.getCellByCoordinate(2,2));
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(2,1));
        targetPlayer3.setPosition(gameHandler.getCellByCoordinate(3,0));
        targetPlayer4.setPosition(gameHandler.getCellByCoordinate(3,1));


        authorPlayer.setAmmoBag(3,3,3);


        //weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(VORTEX_CANNON_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);

        //System.out.println(playerView.getLastStringPrinted());

        //add firemode
        FireModeMessage fireModeMessage = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
        //System.out.println(playerView.getLastStringPrinted());

    }

    @Test
    public void firePositiveNoTargetingWithOptional() throws Exception {
        assertEquals(VortexCannon_1.SELECT_CELL_VORTEX, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(VortexCannon_1.SELECT_FIRST_TARGET_VORTEX, playerView.getLastStringPrinted());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());
        assertEquals(2, targetPlayer1.getCell().getCoordinateX());
        assertEquals(1, targetPlayer1.getCell().getCoordinateY());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.FIRST_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(VortexCannon_1.SELECT_SECOND_TARGET_VORTEX_OPTIONAL, playerView.getLastStringPrinted());

        PlayerMessage playerMessage2 = new PlayerMessage(targetPlayer2.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage2);
        assertEquals(VortexCannon_1.SELECT_THIRD_TARGET_VORTEX_OPTIONAL, playerView.getLastStringPrinted());
        assertEquals(2, targetPlayer2.getCell().getCoordinateX());
        assertEquals(1, targetPlayer2.getCell().getCoordinateY());

        //this one is too distant from the vortex
        PlayerMessage playerMessage3 = new PlayerMessage(targetPlayer3.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage3);
        assertEquals(VortexCannon_1.INVALID_TARGET_VORTEX + VortexCannon_1.SELECT_THIRD_TARGET_VORTEX_OPTIONAL,
                playerView.getLastStringPrinted());
        assertEquals(3, targetPlayer3.getCell().getCoordinateX());  //remains in the same position, too distant
        assertEquals(0, targetPlayer3.getCell().getCoordinateY());

        PlayerMessage playerMessage4 = new PlayerMessage(targetPlayer4.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage4);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());
        assertEquals(2, targetPlayer4.getCell().getCoordinateX());
        assertEquals(1, targetPlayer4.getCell().getCoordinateY());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);

        assertEquals(0,authorPlayer.getDamage().size());
        assertEquals(0,authorPlayer.getMark().getMarkReceived().size());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(1,targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(0,targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());
        assertEquals(1,targetPlayer4.getDamage().size());
        assertEquals(0, targetPlayer4.getMark().getMarkReceived().size());

    }

    @Test
    public void fireNegativeVortexNotVisibleVortex() throws Exception {
        assertEquals(VortexCannon_1.SELECT_CELL_VORTEX, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(3,2,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(VortexCannon_1.CELL_NOT_VISIBLE + VortexCannon_1.SELECT_CELL_VORTEX,
                playerView.getLastStringPrinted());

    }

    @Test
    public void fireNegativeAlreadySelected() throws Exception {
        assertEquals(VortexCannon_1.SELECT_CELL_VORTEX, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(VortexCannon_1.SELECT_FIRST_TARGET_VORTEX, playerView.getLastStringPrinted());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());
        assertEquals(2, targetPlayer1.getCell().getCoordinateX());
        assertEquals(1, targetPlayer1.getCell().getCoordinateY());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.FIRST_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(VortexCannon_1.SELECT_SECOND_TARGET_VORTEX_OPTIONAL, playerView.getLastStringPrinted());

        controller.update(null,playerMessage1);
        assertEquals(VortexCannon_1.PLAYER_ALREADY_SELECTED +
                VortexCannon_1.SELECT_SECOND_TARGET_VORTEX_OPTIONAL, playerView.getLastStringPrinted());

    }

    @Test
    public void fireNegativeTooManyTargets() throws Exception {

        //I put targetPlayer3 in a valid position
        targetPlayer3.setPosition(gameHandler.getCellByCoordinate(2,1));

        assertEquals(VortexCannon_1.SELECT_CELL_VORTEX, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(VortexCannon_1.SELECT_FIRST_TARGET_VORTEX, playerView.getLastStringPrinted());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());
        assertEquals(2, targetPlayer1.getCell().getCoordinateX());
        assertEquals(1, targetPlayer1.getCell().getCoordinateY());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.FIRST_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(VortexCannon_1.SELECT_SECOND_TARGET_VORTEX_OPTIONAL, playerView.getLastStringPrinted());

        PlayerMessage playerMessage2 = new PlayerMessage(targetPlayer2.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage2);
        assertEquals(VortexCannon_1.SELECT_THIRD_TARGET_VORTEX_OPTIONAL, playerView.getLastStringPrinted());
        assertEquals(2, targetPlayer2.getCell().getCoordinateX());
        assertEquals(1, targetPlayer2.getCell().getCoordinateY());

        //this one is too distant from the vortex
        PlayerMessage playerMessage3 = new PlayerMessage(targetPlayer3.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage3);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());
        assertEquals(2, targetPlayer3.getCell().getCoordinateX());
        assertEquals(1, targetPlayer3.getCell().getCoordinateY());

        PlayerMessage playerMessage4 = new PlayerMessage(targetPlayer4.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage4);
        assertEquals(ActionSelectedControllerState.CANT_DO_THIS + Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());
        assertEquals(3, targetPlayer4.getCell().getCoordinateX());
        assertEquals(1, targetPlayer4.getCell().getCoordinateY());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);

        assertEquals(0,authorPlayer.getDamage().size());
        assertEquals(0,authorPlayer.getMark().getMarkReceived().size());
        assertEquals(2,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());
        assertEquals(1,targetPlayer2.getDamage().size());
        assertEquals(0, targetPlayer2.getMark().getMarkReceived().size());
        assertEquals(1,targetPlayer3.getDamage().size());
        assertEquals(0, targetPlayer3.getMark().getMarkReceived().size());
        assertEquals(0,targetPlayer4.getDamage().size());
        assertEquals(0, targetPlayer4.getMark().getMarkReceived().size());

    }

}