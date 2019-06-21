package it.polimi.se2019.controller.actions;

import it.polimi.se2019.controller.ActionSelectedControllerState;
import it.polimi.se2019.controller.Controller;
import it.polimi.se2019.controller.StateController;
import it.polimi.se2019.model.deck.Card;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.NotPresentException;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.ActionMessage;
import it.polimi.se2019.view.ViewControllerMess.CellMessage;
import it.polimi.se2019.view.ViewControllerMess.TeleporterMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GrabTest {

    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private Action action;
    private FireMode lockRifle_1;
    private Cell commonCell;
    private Cell notVisibleCell;
    private StateController stateController;

    @Before
    public void setUp() throws Exception {
        authorPlayer = new Player("TonyStark", new it.polimi.se2019.model.player.Character("IronMan", "yellow"), 2008);
        targetPlayer1 = new Player("SteveRogers", new it.polimi.se2019.model.player.Character("CapAmerica", "blue"), 2011);
        targetPlayer2 = new Player("Hulk", new it.polimi.se2019.model.player.Character("Hulk", "yellow"), 3);
        targetPlayer3 = new Player("Thor", new it.polimi.se2019.model.player.Character("GodOfThunder", "purple"), 4);

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
        action = new Grab(gameHandler, controller);

        controller.setState(new ActionSelectedControllerState(controller, gameHandler, action));
        stateController = controller.getState();

        //author, target 1 and target 2 in the same cell
        commonCell = gameHandler.getCellByCoordinate(0,1);
        authorPlayer.setPosition(commonCell);
        targetPlayer1.setPosition(commonCell);
        targetPlayer2.setPosition(commonCell);

        //target 3 is in another room
        //TODO controlla che sia un'altra statnza!!
        notVisibleCell = gameHandler.getCellByCoordinate(1,2);
        targetPlayer3.setPosition(notVisibleCell);

        authorPlayer.setAmmoBag(0,0,0);
    }

    @Test
    public void correctCallFromController(){

        //System.out.println(playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(1,1,authorPlayer.getID(),playerView);

        controller.update(null, cellMessage);
        //System.out.println(playerView.getLastStringPrinted());
        //System.out.println(authorPlayer.getAmmo());

        assertEquals(1,authorPlayer.getCell().getCoordinateX());
        assertEquals(1,authorPlayer.getCell().getCoordinateY());

        ActionMessage actionMessage = new ActionMessage(Identificator.GRAB, authorPlayer.getID(), playerView);

        controller.update(null, actionMessage);
        //System.out.println(playerView.getLastStringPrinted());

        CellMessage cellMessage2 = new CellMessage(1,0,authorPlayer.getID(),playerView);
        controller.update(null, cellMessage2);
        //System.out.println(playerView.getLastStringPrinted());
        //System.out.println(authorPlayer.getAmmo());
    }

    @Test
    public void tooDistantCell(){

        System.out.println(playerView.getLastStringPrinted());

        CellMessage cellMessage = new CellMessage(3,1,authorPlayer.getID(),playerView);
        controller.update(null, cellMessage);

        assertEquals(Grab.TOO_MUCH_DISTANCE + Grab.CHOOSE_CELL, playerView.getLastStringPrinted());
    }

}