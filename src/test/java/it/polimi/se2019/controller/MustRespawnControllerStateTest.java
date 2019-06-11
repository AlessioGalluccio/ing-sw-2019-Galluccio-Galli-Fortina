package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.PowerupCard;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.view.ViewControllerMess.CardSpawnChooseMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class MustRespawnControllerStateTest {
    private int authorID = 1;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private StateController state;
    private static String MUST_RESPAWN = "Please, do a respawn";

    @Before
    public void setUp() throws Exception {
        gameHandler = mock(GameHandler.class);
        controller = new Controller(gameHandler, null);
        state = new MustRespawnControllerState(controller, gameHandler);
        controller.setState(state);
    }

    @Test
    public void handleCardSpawn() {
        PowerupCard cardChoosenMock = mock(PowerupCard.class);
        PowerupCard cardDiscardedMock = mock(PowerupCard.class);
        CardSpawnChooseMessage cardSpawnChooseMessage = new CardSpawnChooseMessage(cardChoosenMock, cardDiscardedMock,
                                                                                    authorID, playerView);
        String response = state.handle(cardSpawnChooseMessage);
        verify(gameHandler, times(1)).nextTurn();
        //TODO verify(controller, times(1)).setState(any(StateController.class));
        assertEquals(MUST_RESPAWN, response);
    }
}