package it.polimi.se2019.model.player;

public class PowerupNotNeededException extends Exception {
    private static final long serialVersionUID = 87521874224155148L;

    /**
     * constructor
     */
    PowerupNotNeededException(){
        super();
    }

    /**
     * constructor
     * @param s the message
     */
    PowerupNotNeededException(String s) {
        super(s);
    }
}
