package it.polimi.se2019.model.player;

public class NotPresentException extends Exception {

    private static final long serialVersionUID = -8311728848362189155L;

    public NotPresentException() {
        super();
    }

    public NotPresentException(String s) {
        super(s);
    }
}
