package it.polimi.se2019.model.player;

public class YouDeadException extends Exception {
    private static final long serialVersionUID = -4213125243495368095L;

    /**
     * constructor
     */
    public YouDeadException() {
        super();
    }

    /**
     * constructor
     * @param s the message
     */
    public YouDeadException(String s) {
        super(s);
    }
}
