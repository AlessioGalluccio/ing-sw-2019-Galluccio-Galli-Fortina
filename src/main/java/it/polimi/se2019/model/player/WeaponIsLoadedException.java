package it.polimi.se2019.model.player;

public class WeaponIsLoadedException extends Exception{
    private static final long serialVersionUID = -5576423842259048518L;

    /**
     * constructor
     */
    WeaponIsLoadedException() {
        super();
    }

    /**
     * constructor
     */
    WeaponIsLoadedException(String s) {
        super(s);
    }
}
