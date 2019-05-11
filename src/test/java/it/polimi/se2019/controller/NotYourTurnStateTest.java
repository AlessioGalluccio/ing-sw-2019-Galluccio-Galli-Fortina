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
        Player temp = new Player("Bob", character1, 0);
        ArrayList<Player> players = new ArrayList<>();
        players.add(temp);
        GameHandler gameHandler = new GameHandler(players);
        this.controller = new Controller(gameHandler);
        this.controller.setState(new NotYourTurnState(controller, gameHandler));
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
    public void handleIsTurnPlayerNow() {
        //ViewControllerMessage msg = new CellMessage(1,2);
        //stateController.handle(msg);


    }
}