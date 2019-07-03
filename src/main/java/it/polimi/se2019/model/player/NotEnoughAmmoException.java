package it.polimi.se2019.model.player;

public class NotEnoughAmmoException extends Exception {
    private static final long serialVersionUID = -6154670360415699911L;

    /**
     * constructor
     */
    public NotEnoughAmmoException() {
        super();
    }

    /**
     * constructor
     * @param s the message
     */
    public NotEnoughAmmoException(String s) {
        super(s);
    }

}
