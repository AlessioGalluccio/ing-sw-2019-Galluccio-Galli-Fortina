package it.polimi.se2019.controller;

import it.polimi.se2019.model.deck.firemodes.CyberBlade_1;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.view.ViewControllerMess.FireModeMessage;
import it.polimi.se2019.view.ViewControllerMess.ViewControllerMessage;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotEmptyControllerStateTest {

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
        ViewControllerMessage msg = new FireModeMessage(Identificator.CYBERBLADE_1);
        boolean value = isFireModePositive();
    }
}