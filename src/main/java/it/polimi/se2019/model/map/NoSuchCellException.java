package it.polimi.se2019.model.map;

public class NoSuchCellException extends Exception {
    public NoSuchCellException() {
    }

    public NoSuchCellException(String message) {
        super(message);
    }
}
