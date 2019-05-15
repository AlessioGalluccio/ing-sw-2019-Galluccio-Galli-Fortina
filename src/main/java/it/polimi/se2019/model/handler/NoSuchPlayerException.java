package it.polimi.se2019.model.handler;

public class NoSuchPlayerException extends Exception {
    public NoSuchPlayerException() {
    }

    public NoSuchPlayerException(String message) {
        super(message);
    }
}
