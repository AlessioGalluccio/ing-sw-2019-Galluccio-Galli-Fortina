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

    @Before
    public void setUp() throws Exception {
        gameHandler = mock(GameHandler.class);
        controller = new Controller(gameHandler);
        state = new MustRespawnControllerState(controller, gameHandler);
        controller.setState(state);
    }

    @Test
    public void handleCardSpawn() {
        PowerupCard cardChoosenMock = mock(PowerupCard.class);
        PowerupCard cardDiscardedMock = mock(PowerupCard.class);
        CardSpawnChooseMessage cardSpawnChooseMessage = new CardSpawnChooseMessage(cardChoosenMock, cardDiscardedMock,
                                                                                    authorID, playerView);
        state.handle(cardSpawnChooseMessage);
        verify(gameHandler, times(1)).nextTurn();
        //TODO verify(controller, times(1)).setState(any(StateController.class));
    }
}