package it.polimi.se2019.model.deck;

class AlreadyDeckException extends RuntimeException {
    AlreadyDeckException(String message) {
        super(message);
    }
}
