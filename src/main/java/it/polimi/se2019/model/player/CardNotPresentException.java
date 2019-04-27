package it.polimi.se2019.model.player;

public class CardNotPresentException extends Exception {

    CardNotPresentException() {
        super();
    }

    CardNotPresentException(String s) {
        super(s);
    }
}
