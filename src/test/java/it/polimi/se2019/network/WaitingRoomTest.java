package it.polimi.se2019.network;

import it.polimi.se2019.network.messages.LoginMessage;
import it.polimi.se2019.network.socket.SocketHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class WaitingRoomTest {
    private WaitingRoom waitingRoom;

    @Before
    public void setUp() {
        waitingRoom = WaitingRoom.create(90);
    }

    @Test
    public void uniqueNickname() {

        SocketHandler mock =  mock(SocketHandler.class);
        waitingRoom.handleSettingMessage(1, 2, false, mock);

        waitingRoom.receiveMessage(new LoginMessage("Steve Jobs"), mock);
        assertEquals(1, waitingRoom.getPlayerWaiting().size());
        waitingRoom.handleLoginMessage("Steve Woz", 0, mock);
        assertEquals(2, waitingRoom.getPlayerWaiting().size());
        waitingRoom.handleLoginMessage("Steve Woz", 0, mock);
        assertEquals(2, waitingRoom.getPlayerWaiting().size());

        waitingRoom.handleLoginMessage("Craig Federighi", 0, mock);
        waitingRoom.handleLoginMessage("Tim Cook", 0, mock);
        waitingRoom.handleLoginMessage("Phil Schiller", 0, mock);
        assertEquals(0, waitingRoom.getPlayerWaiting().size());
        waitingRoom.handleLoginMessage("Jonathan Ive", 0, mock);
        assertEquals(1, waitingRoom.getPlayerWaiting().size());
        waitingRoom.receiveMessage(new LoginMessage("Steve Jobs"), mock);
        assertEquals(1, waitingRoom.getPlayerWaiting().size());

    }
}