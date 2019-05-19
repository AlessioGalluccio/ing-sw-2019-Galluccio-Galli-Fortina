package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.deck.firemodes.CyberBlade_1;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.StringAndMessage;
import it.polimi.se2019.view.ViewControllerMess.FireModeMessage;
import it.polimi.se2019.view.ViewControllerMess.TeleporterMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import it.polimi.se2019.view.remoteView.PlayerView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ActionSelectedControllerStateTest {
    private int authorID = 1;
    private PlayerView playerView;
    private GameHandler gameHandler;
    private Controller controller;
    private ActionSelectedControllerState state;

    @Before
    public void setUp() throws Exception {
        gameHandler = mock(GameHandler.class);
        controller = new Controller(gameHandler);
        state = new ActionSelectedControllerState(controller, gameHandler);
        controller.setState(state);
    }

    @Test
    public void correctCallOfHandleFromController(){
        ActionSelectedControllerState stateMock = mock(ActionSelectedControllerState.class);
        GameHandler gameHandler = mock(GameHandler.class);
        Controller controller = new Controller(gameHandler);
        controller.setState(stateMock);
        StringAndMessage stringAndMessage = new StringAndMessage(Identificator.TELEPORTER_MESSAGE, "ok", true);
        controller.addMessageListExpected(stringAndMessage);

        TeleporterCard teleporterCard = mock(TeleporterCard.class);
        PlayerView playerView = mock(PlayerView.class);
        TeleporterMessage teleporterMessage = new TeleporterMessage(teleporterCard, 1, playerView);

        controller.update(null, teleporterMessage);

        verify(stateMock).handle(ArgumentMatchers.eq(teleporterMessage));

    }

    @Test
    public void handleAction() {
        StringAndMessage stringAndMessage = new StringAndMessage(Identificator.TELEPORTER_MESSAGE, "ok", true);
        controller.addMessageListExpected(stringAndMessage);
        TeleporterMessage teleporterMock = mock(TeleporterMessage.class);
        when(teleporterMock.getMessageID()).thenReturn(Identificator.TELEPORTER_MESSAGE);
        state.handle(teleporterMock);
        verify(teleporterMock, times(1)).handle(state);
    }

    @Test
    public void handleCardSpawn() {
    }

    @Test
    public void handleCell() {
    }

    @Test
    public void handleFiremode() {
    }

    @Test
    public void handle4() {
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