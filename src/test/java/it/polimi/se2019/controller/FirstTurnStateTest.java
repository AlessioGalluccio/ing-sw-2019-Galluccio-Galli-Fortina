package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.firemodes.FireMode;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.map.Cell;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.network.Server;
import it.polimi.se2019.view.ViewControllerMess.*;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FirstTurnStateTest {

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
        authorPlayer = new Player("TonyStark", null, 2008);
        targetPlayer1 = new Player("SteveRogers", null, 2011);
        targetPlayer2 = new Player("Hulk", null, 3);
        targetPlayer3 = new Player("Thor", null, 4);

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

        controller.setState(new FirstTurnState(controller, gameHandler));
        stateController = controller.getState();
    }

    @Test
    public void handleAction() {
        //TODO errore in ottenere l'azione, manca il metodo!!!
        assertEquals(FirstTurnState.CHARACTER_REQUEST, playerView.getLastStringPrinted());

        CharacterMessage characterMessage = new CharacterMessage(1, authorPlayer.getID(),playerView);
        controller.update(null,characterMessage);
        assertEquals(FirstTurnState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());


        DiscardPowerupMessage discardPowerupMessage = new DiscardPowerupMessage(authorPlayer.getPowerupCardList().get(0), authorPlayer.getID(), playerView);
        controller.update(null, discardPowerupMessage);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        assertEquals(1, controller.getAuthor().getPowerupCardList().size());

        assertEquals(true, controller.getState() instanceof EmptyControllerState);

        ActionMessage actionMessage = new ActionMessage(Identificator.MOVE,authorPlayer.getID(),playerView);
        controller.update(null, actionMessage);
        System.out.println(playerView.getLastStringPrinted());

        assertEquals(true, controller.getState() instanceof ActionSelectedControllerState);


    }

    @Test
    public void handleActionWithCharacterAlreadySelected() {
        //TODO errore in ottenere l'azione, manca il metodo!!!

        authorPlayer.setCharacter( new Character("IronMan", "yellow"));

        controller.setState(new FirstTurnState(controller, gameHandler));
        assertEquals(FirstTurnState.POWERUP_DISCARD_REQUEST, playerView.getLastStringPrinted());

        DiscardPowerupMessage discardPowerupMessage = new DiscardPowerupMessage(authorPlayer.getPowerupCardList().get(0), authorPlayer.getID(), playerView);
        controller.update(null, discardPowerupMessage);
        assertEquals(EmptyControllerState.SELECT_ACTION_REQUEST, playerView.getLastStringPrinted());
        assertEquals(1, controller.getAuthor().getPowerupCardList().size());

        assertEquals(true, controller.getState() instanceof EmptyControllerState);

        ActionMessage actionMessage = new ActionMessage(Identificator.MOVE,authorPlayer.getID(),playerView);
        controller.update(null, actionMessage);
        System.out.println(playerView.getLastStringPrinted());

        assertEquals(true, controller.getState() instanceof ActionSelectedControllerState);


    }


}