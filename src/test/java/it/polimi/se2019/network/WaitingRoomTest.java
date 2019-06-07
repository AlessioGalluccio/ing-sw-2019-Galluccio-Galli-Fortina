package it.polimi.se2019.network;

import it.polimi.se2019.network.configureMessage.LoginMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WaitingRoomTest {
    private WaitingRoom waitingRoom;

    @Before
    public void setUp() {
        waitingRoom = WaitingRoom.create(90);
    }

    @Test
    public void uniqueNickname() {
        waitingRoom.receiveMessage(new LoginMessage("Steve Jobs"), null);
        assertEquals(1, waitingRoom.getPlayerWaiting().size());
        waitingRoom.handleLoginMessage("Steve Woz", 0, null);
        assertEquals(2, waitingRoom.getPlayerWaiting().size());
        waitingRoom.handleLoginMessage("Steve Woz", 0, null);
        assertEquals(2, waitingRoom.getPlayerWaiting().size());

        waitingRoom.handleLoginMessage("Craig Federighi", 0, null);
        waitingRoom.handleLoginMessage("Tim Cook", 0, null);
        waitingRoom.handleLoginMessage("Phil Schiller", 0, null);
        assertEquals(0, waitingRoom.getPlayerWaiting().size());
        waitingRoom.handleLoginMessage("Jonathan Ive", 0, null);
        assertEquals(1, waitingRoom.getPlayerWaiting().size());
        waitingRoom.receiveMessage(new LoginMessage("Steve Jobs"), null);
        assertEquals(1, waitingRoom.getPlayerWaiting().size());

    }
}