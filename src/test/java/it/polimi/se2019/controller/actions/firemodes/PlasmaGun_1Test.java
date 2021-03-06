package it.polimi.se2019.controller.actions.firemodes;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.deck.WeaponCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
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

public class PlasmaGun_1Test {
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

    private final static int PLASMAGUN_WEAPON_ID = 17;


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
        targetPlayer1.setPosition(gameHandler.getCellByCoordinate(2,1)); //visible
        targetPlayer2.setPosition(gameHandler.getCellByCoordinate(1,0)); //not visible
        targetPlayer3.setPosition(gameHandler.getCellByCoordinate(3,0)); //not used
        targetPlayer4.setPosition(gameHandler.getCellByCoordinate(3,1)); //not used


        authorPlayer.setAmmoBag(3,3,3);


        //weapon
        WeaponCard weapon = gameHandler.getWeaponCardByID(PLASMAGUN_WEAPON_ID);
        weapon.reload();
        authorPlayer.addWeaponCard(weapon);
        WeaponMessage weaponMessage = new WeaponMessage(weapon,authorPlayer.getID(), playerView);
        controller.update(null, weaponMessage);

        //add firemode
        FireModeMessage fireModeMessage = new FireModeMessage(1, authorPlayer.getID(), playerView);
        controller.update(null, fireModeMessage);
        //System.out.println(playerView.getLastStringPrinted());

    }

    @Test
    public void firePositiveNoTargetingNoOptional() throws Exception {
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

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
    public void firePositiveNoTargetingNoOptionalWithMark() throws Exception {
        targetPlayer1.receiveMarkBy(authorPlayer);
        assertEquals(1, targetPlayer1.getMark().getMarkReceived().size()); // we controll that the target has a mark before the firemode
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
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
    public void firePositiveNoTargetingOptional1() throws Exception {
        authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,2)); //author is put in a cell where he can't see the target
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.FIRST_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(PlasmaGun_1.SELECT_CELL_TO_MOVE, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());
        assertEquals(2, authorPlayer.getCell().getCoordinateX());
        assertEquals(1,authorPlayer.getCell().getCoordinateY());

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
    public void firePositiveNoTargetingOptional1AdjacentCell() throws Exception {
        authorPlayer.setPosition(gameHandler.getCellByCoordinate(2,2)); //author is put in a adjacent cell
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.FIRST_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(PlasmaGun_1.SELECT_CELL_TO_MOVE, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());
        assertEquals(2, authorPlayer.getCell().getCoordinateX());
        assertEquals(1,authorPlayer.getCell().getCoordinateY());

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
    public void firePositiveNoTargetingOptional2() throws Exception {
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.SECOND_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
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
    public void firePositiveNoTargetingOptional1Optional2WithMarks() throws Exception {
        targetPlayer1.receiveMarkBy(authorPlayer);
        targetPlayer1.receiveMarkBy(authorPlayer);
        assertEquals(2, targetPlayer1.getMark().getMarkReceived().size()); // we controll that the target has two marka before the firemode


        authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,2)); //author is put in a cell where he can't see the target
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.FIRST_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(PlasmaGun_1.SELECT_CELL_TO_MOVE, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());
        assertEquals(2, authorPlayer.getCell().getCoordinateX());
        assertEquals(1,authorPlayer.getCell().getCoordinateY());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        OptionalMessage optionalMessage2 = new OptionalMessage(Identificator.SECOND_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage2);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);

        assertEquals(0,authorPlayer.getDamage().size());
        assertEquals(0,authorPlayer.getMark().getMarkReceived().size());
        assertEquals(5,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

    }

    @Test
    public void firePositiveWithTargetingOptional1Optional2WithMarks() throws Exception {
        targetPlayer1.receiveMarkBy(authorPlayer);
        targetPlayer1.receiveMarkBy(authorPlayer);
        assertEquals(2, targetPlayer1.getMark().getMarkReceived().size()); // we controll that the target has two marka before the firemode


        authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,2)); //author is put in a cell where he can't see the target
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.FIRST_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(PlasmaGun_1.SELECT_CELL_TO_MOVE, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,1,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());
        assertEquals(2, authorPlayer.getCell().getCoordinateX());
        assertEquals(1,authorPlayer.getCell().getCoordinateY());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        OptionalMessage optionalMessage2 = new OptionalMessage(Identificator.SECOND_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage2);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        //Targeting scope with red cost
        TargetingScopeCard card = new TargetingScopeCard(ColorRYB.BLUE, 1,1);
        authorPlayer.addPowerupCard(card);
        TargetingScopeMessage targetingScopeMessage = new TargetingScopeMessage(card, ColorRYB.RED, authorPlayer.getID(), playerView);
        controller.update(null, targetingScopeMessage);
        controller.update(null,playerMessage1);
        assertEquals(Shoot.LAST_MESSAGE, playerView.getLastStringPrinted());

        FireMessage fireMessage = new FireMessage(authorPlayer.getID(), playerView);
        controller.update(null, fireMessage);

        assertEquals(0,authorPlayer.getDamage().size());
        assertEquals(0,authorPlayer.getMark().getMarkReceived().size());
        assertEquals(6,targetPlayer1.getDamage().size());
        assertEquals(0, targetPlayer1.getMark().getMarkReceived().size());

        //ammo cost
        assertEquals(2, authorPlayer.getAmmo().getRedAmmo());
        assertEquals(3, authorPlayer.getAmmo().getYellowAmmo());
        assertEquals(2, authorPlayer.getAmmo().getBlueAmmo());

    }

    @Test
    public void fireNegativeNotVisibleTarget() throws Exception {
        authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,2)); //author is put in a cell where he can't see the target
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        PlayerMessage playerMessage1 = new PlayerMessage(targetPlayer1.getID(), authorPlayer.getID(), playerView);
        controller.update(null,playerMessage1);
        assertEquals(PlasmaGun_1.NOT_VISIBLE_TARGET + PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

    }

    @Test
    public void fireNegativeTooDistantCell() throws Exception {
        authorPlayer.setPosition(gameHandler.getCellByCoordinate(1,2)); //author is put in a cell where he can't see the target
        assertEquals(PlasmaGun_1.SELECT_VISIBLE_TARGET, playerView.getLastStringPrinted());

        OptionalMessage optionalMessage = new OptionalMessage(Identificator.FIRST_OPTIONAL, authorPlayer.getID(), playerView);
        controller.update(null, optionalMessage);
        assertEquals(PlasmaGun_1.SELECT_CELL_TO_MOVE, playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(2,0,authorPlayer.getID(), playerView);
        controller.update(null, cellMessage);
        assertEquals(PlasmaGun_1.CELL_TOO_DISTANT_PLASMA + PlasmaGun_1.SELECT_CELL_TO_MOVE, playerView.getLastStringPrinted());
        //same position of before
        assertEquals(1, authorPlayer.getCell().getCoordinateX());
        assertEquals(2,authorPlayer.getCell().getCoordinateY());

    }

}