package it.polimi.se2019.controller;

import it.polimi.se2019.model.handler.GameHandler;
import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.ViewControllerMess.CellMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NotYourTurnStateTest {

    private Controller controller;
    private StateController stateController;

    @Before
    public void setUp() throws Exception {
        Character character1 = new Character("Character1", "Yellow");
        Player player = new Player("Bob", character1, 0);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        GameHandler gameHandler = new GameHandler(players, 8);
        this.controller = new Controller(gameHandler);
        controller.setAuthor(player);
        this.controller.setState(new NotYourTurnState(controller, gameHandler));
        this.stateController = controller.getState();
    }

    @Test
    public void handleAction() {
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
    public void handleLogin() {
    }

    @Test
    public void handleNewton() {
    }

    @Test
    public void handleNope() {
    }

    @Test
    public void handlePlayer() {
    }

    @Test
    public void handleReload() {
    }

    @Test
    public void handleTagback() {
    }

    @Test
    public void handleTargeting() {
    }

    @Test
    public void handleTeleporter() {
    }

    @Test
    public void handleReconnectionDisconnected(){
        stateController.handleReconnection(false);
        assertEquals( true, controller.getState() instanceof DisconnectedControllerState);
    }

    @Test
    public void handleReconnectionNoEffect(){
        stateController.handleReconnection(true);
        assertEquals(false, controller.getState() instanceof DisconnectedControllerState );
        assertEquals(true, controller.getState() instanceof NotYourTurnState);
    }

    @Test
    public void handleIsTurnPlayerNow() {
        //ViewControllerMessage msg = new CellMessage(1,2);
        //stateController.handle(msg);


    }
}