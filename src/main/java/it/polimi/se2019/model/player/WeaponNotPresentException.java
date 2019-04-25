package it.polimi.se2019.model.player;

public class WeaponNotPresentException extends Exception {

    WeaponNotPresentException() {
        super();
    }

    WeaponNotPresentException(String s) {
        super(s);
    }
}
