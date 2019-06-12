package it.polimi.se2019.controller;

import it.polimi.se2019.controller.actions.Action;
import it.polimi.se2019.controller.actions.FiremodeOfOnlyMarksException;
import it.polimi.se2019.controller.actions.Shoot;
import it.polimi.se2019.controller.actions.WrongInputException;
import it.polimi.se2019.model.deck.TargetingScopeCard;
import it.polimi.se2019.model.deck.TeleporterCard;
import it.polimi.se2019.model.deck.firemodes.CyberBlade_1;
import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.AmmoBag;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.NotEnoughAmmoException;
import it.polimi.se2019.model.player.NotPresentException;
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
    private PlayerView playerViewMock;
    private GameHandler gameHandler;
    private Controller controller;
    private StateController state;
    private Action actionMock;

    @Before
    public void setUp() throws Exception {
        gameHandler = mock(GameHandler.class);
        playerViewMock = mock(PlayerView.class);
        controller = new Controller(gameHandler, null);
        controller.setPlayerView(playerViewMock);
        this.actionMock = mock(Shoot.class);
        state = new ActionSelectedControllerState(controller, gameHandler, actionMock);
        controller.setState(state);
    }

    @Test
    public void correctCallOfHandleFromController(){
        ActionSelectedControllerState stateMock = mock(ActionSelectedControllerState.class);
        GameHandler gameHandler = mock(GameHandler.class);
        Controller controller = new Controller(gameHandler,  null);
        controller.setState(stateMock);
        StringAndMessage stringAndMessage = new StringAndMessage(Identificator.TELEPORTER_MESSAGE,
                                                                        "ok");
        controller.addMessageListExpected(stringAndMessage);

        TeleporterCard teleporterCard = mock(TeleporterCard.class);
        PlayerView playerView = mock(PlayerView.class);
        TeleporterMessage teleporterMessage = new TeleporterMessage(teleporterCard, 1, playerView);

        controller.update(null, teleporterMessage);

        verify(stateMock).handle(ArgumentMatchers.eq(teleporterMessage));

    }

    @Test
    public void handleAction() {

    }

    @Test
    public void handleCardSpawn() {
    }

    @Test
    public void handleTargetingWrongInput(){
        //TODO controllare eccezioni
        int targetingID = 1;
        int IDtype = 1;
        TargetingScopeCard targetingScopeCard = new TargetingScopeCard(ColorRYB.RED,targetingID,IDtype);
        AmmoBag cost = new AmmoBag(0,0,1);
        state.handleTargeting(targetingScopeCard, cost);

        /*
        try {
            doThrow(new WrongInputException()).when(actionMock).addTargetingScope(anyObject(), anyObject());
        } catch (Exception e) {
            //DO nothing, it's needed for IntelliJ
        }

        state.handleTargeting(targetingScopeCard, cost);
        verify(playerViewMock, times(1)).printFromController("You have already selected this, you can't use again");
        */
        try{
            verify(actionMock, times(1)).addTargetingScope(targetingID, cost);
        }catch(Exception e){
            //do nothing
        }



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