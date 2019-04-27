package it.polimi.se2019.model.player;

public class NotEnoughAmmoException extends Exception {
    NotEnoughAmmoException() {
        super();
    }
    NotEnoughAmmoException(String s) {
        super(s);
    }

}
