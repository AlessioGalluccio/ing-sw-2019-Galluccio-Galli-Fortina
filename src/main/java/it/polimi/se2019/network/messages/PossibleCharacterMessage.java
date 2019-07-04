package it.polimi.se2019.network.messages;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;
import java.util.List;

/**
 * @author Galli
 */
public class PossibleCharacterMessage implements HandlerNetworkMessage, Serializable {

    private static final long serialVersionUID = 3661734970314913421L;
    private List<Character> possibleCharacter;

    public PossibleCharacterMessage(List<Character> possibleCharacter) {
        this.possibleCharacter = possibleCharacter;
    }

    /**
     * Only the message itself can't know how to handle himself.
     * This method call the right method of the client who receive this message in order to handle and forward him correctly.
     * @param client The Client object who has to handle this message
     */
    @Override
    public void handleMessage(Client client) {
        client.handleCharacterMessage(possibleCharacter);
    }
}
