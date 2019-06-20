package it.polimi.se2019.model.map;

public class NotHereException extends RuntimeException {
    private static final long serialVersionUID = 162771958366298808L;

    public NotHereException(String message) {
        super(message);
    }
}
