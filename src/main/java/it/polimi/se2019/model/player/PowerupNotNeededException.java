package it.polimi.se2019.model.player;

public class PowerupNotNeededException extends Exception {
    PowerupNotNeededException(){
        super();
    }

    PowerupNotNeededException(String s) {
        super(s);
    }
}
