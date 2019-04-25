package it.polimi.se2019.model.player;

public class WrongColorException extends Exception {
    WrongColorException() {
        super();
    }

    WrongColorException(String s) {
        super(s);
    }
}
