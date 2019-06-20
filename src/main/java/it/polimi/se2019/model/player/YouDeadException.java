package it.polimi.se2019.model.player;

public class YouDeadException extends Exception {
    private static final long serialVersionUID = -4213125243495368095L;

    public YouDeadException() {
        super();
    }

    public YouDeadException(String s) {
        super(s);
    }
}
