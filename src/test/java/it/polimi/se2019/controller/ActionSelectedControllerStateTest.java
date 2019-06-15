package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.model.deck.FireMode;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.*;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.TeleporterMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ActionSelectedControllerStateTest {

    private Player authorPlayer;
    private Player targetPlayer1;
    private Player targetPlayer2;
    private Player targetPlayer3;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private Shoot shoot;
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
        Shoot shoot = new Shoot(gameHandler, controller);

        controller.setState(new ActionSelectedControllerState(controller, gameHandler, shoot));
        stateController = controller.getState();
    }

    @Test
    public void correctCallOfHandleFromController(){

        TeleporterCard teleporterCard = new TeleporterCard(ColorRYB.BLUE,1,1);


        TeleporterMessage teleporterMessage = new TeleporterMessage(teleporterCard, 1, playerView);

        controller.update(null, teleporterMessage);

        assertEquals(StateController.CANT_DO_THIS + controller.getCopyMessageListExpected().get(0).getString(),
                playerView.getLastStringPrinted());

    }
















    @Test
    public void handleAction() {

    }

    @Test
    public void handleCardSpawn() {
    }

    @Test
    public void handleTargetingWrongInput(){




    }

    @Test
    public void handleFiremode() {
    }


    @Test
    public void handle5() {
    }

    @Test
    public void handle6() {
    }

    @Test
    public void handle7() {
    }

    @Test
    public void handle8() {
    }

    @Test
    public void handle9() {
    }

    @Test
    public void handle10() {
    }

    @Test
    public void handle11() {
    }

    @Test
    public void handle12() {
    }


    @Test
    public void isFireModePositive() {
        //ViewControllerMessage msg = new FireModeMessage(Identificator.CYBERBLADE_1, authorID, playerView);
        //boolean value = isFireModePositive(); E' stato commentato perché non compilava
    }
}