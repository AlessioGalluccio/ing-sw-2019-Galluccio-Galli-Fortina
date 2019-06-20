package it.polimi.se2019.model.player;

public class TooManyException extends Exception {

    private static final long serialVersionUID = -1477742108064011104L;

    public TooManyException(String message) {
        super(message);
    }
}
