package it.polimi.se2019.model.player;

public class NotEnoughAmmoException extends Exception {
    public NotEnoughAmmoException() {
        super();
    }
    public NotEnoughAmmoException(String s) {
        super(s);
    }

}
