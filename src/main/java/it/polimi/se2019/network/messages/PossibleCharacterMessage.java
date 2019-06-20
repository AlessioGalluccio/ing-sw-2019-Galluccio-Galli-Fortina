package it.polimi.se2019.network.messages;

import it.polimi.se2019.model.player.Character;
import it.polimi.se2019.network.Client;
import it.polimi.se2019.network.HandlerNetworkMessage;

import java.io.Serializable;
import java.util.List;

public class PossibleCharacterMessage implements HandlerNetworkMessage, Serializable {

    private static final long serialVersionUID = 3661734970314913421L;
    private List<Character> possibleCharacter;

    public PossibleCharacterMessage(List<Character> possibleCharacter) {
        this.possibleCharacter = possibleCharacter;
    }

    public List<Character> getPossibleCharacter() {
        return possibleCharacter;
    }

    @Override
    public void handleMessage(Client client) {
        client.handleCharacterMessage(possibleCharacter);
    }
}
