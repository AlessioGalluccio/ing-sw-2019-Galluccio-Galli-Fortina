package it.polimi.se2019.model.deck;

public class NoSuchCardException extends Exception {
    public NoSuchCardException() {
    }

    public NoSuchCardException(String message) {
        super(message);
    }
}
