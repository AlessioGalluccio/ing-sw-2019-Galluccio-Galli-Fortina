package it.polimi.se2019.model.player;

public class NotEnoughAmmoException extends Exception {
    private static final long serialVersionUID = -6154670360415699911L;

    public NotEnoughAmmoException() {
        super();
    }
    public NotEnoughAmmoException(String s) {
        super(s);
    }

}
