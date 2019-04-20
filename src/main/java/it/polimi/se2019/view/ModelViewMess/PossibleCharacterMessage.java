package it.polimi.se2019.view.ModelViewMess;

import java.util.ArrayList;

public class PossibleCharacterMessage implements ModelViewMessage {

    private ArrayList<Character> possibleCharacter;

    public PossibleCharacterMessage(ArrayList<Character> possibleCharacter) {

        this.possibleCharacter = possibleCharacter;
    }

    public ArrayList<Character> getPossibleCharacter() {
        return possibleCharacter;
    }
}
