package it.polimi.se2019.model.player;

public class WeaponIsLoadedException extends Exception{
    WeaponIsLoadedException() {
        super();
    }

    WeaponIsLoadedException(String s) {
        super(s);
    }
}
