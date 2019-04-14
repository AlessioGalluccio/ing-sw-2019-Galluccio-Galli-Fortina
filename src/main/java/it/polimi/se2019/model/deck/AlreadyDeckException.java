package it.polimi.se2019.model.deck;

public class AlreadyDeckException extends RuntimeException {
    AlreadyDeckException(String message) {
        super(message);
    }
}
